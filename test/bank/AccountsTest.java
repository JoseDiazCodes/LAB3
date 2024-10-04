package bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SavingsAccountTest {
  private SavingsAccount savingsAccount;

  @BeforeEach
  void setUp() {
    savingsAccount = new SavingsAccount(100.00);
  }

  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new SavingsAccount(-10.00));
    assertThrows(IllegalArgumentException.class, () -> new SavingsAccount(0.00));
    assertDoesNotThrow(() -> new SavingsAccount(0.01));
  }

  @Test
  void testDeposit() {
    savingsAccount.deposit(50.00);
    assertEquals(150.00, savingsAccount.getBalance(), 0.001);
    assertThrows(IllegalArgumentException.class, () -> savingsAccount.deposit(-10.00));
  }

  @Test
  void testWithdraw() {
    assertTrue(savingsAccount.withdraw(50.00));
    assertEquals(50.00, savingsAccount.getBalance(), 0.001);
    assertFalse(savingsAccount.withdraw(100.00));
    assertFalse(savingsAccount.withdraw(-10.00));
    assertFalse(savingsAccount.withdraw(0.00));
  }

  @Test
  void testWithdrawLimit() {
    for (int i = 0; i < 6; i++) {
      assertTrue(savingsAccount.withdraw(1.00));
    }
    assertEquals(94.00, savingsAccount.getBalance(), 0.001);

    assertTrue(savingsAccount.withdraw(1.00)); // 7th withdrawal
    assertEquals(93.00, savingsAccount.getBalance(), 0.001);

    savingsAccount.performMonthlyMaintenance();
    assertEquals(79.00, savingsAccount.getBalance(), 0.001); // 93 - 14 (penalty) = 79
  }

  @Test
  void testGetBalance() {
    assertEquals(100.00, savingsAccount.getBalance(), 0.001);
    savingsAccount.deposit(50.00);
    assertEquals(150.00, savingsAccount.getBalance(), 0.001);
    savingsAccount.withdraw(25.00);
    assertEquals(125.00, savingsAccount.getBalance(), 0.001);
  }

  @Test
  void testPerformMonthlyMaintenance() {
    // Perform 6 withdrawals (should not incur penalty)
    for (int i = 0; i < 6; i++) {
      assertTrue(savingsAccount.withdraw(1.00));
    }
    assertEquals(94.00, savingsAccount.getBalance(), 0.001);
    assertEquals(6, savingsAccount.getWithdrawCount());

    savingsAccount.performMonthlyMaintenance();
    assertEquals(94.00, savingsAccount.getBalance(), 0.001); // No penalty
    assertEquals(0, savingsAccount.getWithdrawCount()); // Count reset

    // Perform 7 withdrawals (should incur penalty)
    for (int i = 0; i < 7; i++) {
      assertTrue(savingsAccount.withdraw(1.00));
    }
    assertEquals(87.00, savingsAccount.getBalance(), 0.001);
    assertEquals(7, savingsAccount.getWithdrawCount());

    savingsAccount.performMonthlyMaintenance();
    assertEquals(73.00, savingsAccount.getBalance(), 0.001); // 87 - 14 (penalty) = 73
    assertEquals(0, savingsAccount.getWithdrawCount()); // Count reset
  }

  @Test
  void testNegativeWithdrawal() {
    assertFalse(savingsAccount.withdraw(-10.00));
    assertEquals(100.00, savingsAccount.getBalance(), 0.001);
    assertEquals(0, savingsAccount.getWithdrawCount());
  }

  @Test
  void testToString() {
    assertEquals("$100.00", savingsAccount.toString());
    savingsAccount.deposit(0.50);
    assertEquals("$100.50", savingsAccount.toString());
  }
}

class CheckingAccountTest {
  private CheckingAccount checkingAccount;

  @BeforeEach
  void setUp() {
    checkingAccount = new CheckingAccount(100.00);
  }

  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new CheckingAccount(-10.00));
    assertThrows(IllegalArgumentException.class, () -> new CheckingAccount(0.00));
    assertDoesNotThrow(() -> new CheckingAccount(0.01));
  }

  @Test
  void testDeposit() {
    checkingAccount.deposit(50.00);
    assertEquals(150.00, checkingAccount.getBalance(), 0.001);
    assertThrows(IllegalArgumentException.class, () -> checkingAccount.deposit(-10.00));
  }

  @Test
  void testWithdraw() {
    assertTrue(checkingAccount.withdraw(50.00));
    assertEquals(50.00, checkingAccount.getBalance(), 0.001);
    assertFalse(checkingAccount.withdraw(100.00));
    assertFalse(checkingAccount.withdraw(-10.00));
    assertFalse(checkingAccount.withdraw(0.00));
  }

  @Test
  void testGetBalance() {
    assertEquals(100.00, checkingAccount.getBalance(), 0.001);
    checkingAccount.deposit(50.00);
    assertEquals(150.00, checkingAccount.getBalance(), 0.001);
    checkingAccount.withdraw(25.00);
    assertEquals(125.00, checkingAccount.getBalance(), 0.001);
  }

  @Test
  void testBalanceBelowMinimumThenAbove() {
    checkingAccount.deposit(100.00);
    assertTrue(checkingAccount.withdraw(150.00)); // Balance falls to $50
    checkingAccount.deposit(100.00); // Balance back to $150
    checkingAccount.performMonthlyMaintenance();
    assertEquals(145.00, checkingAccount.getBalance(), 0.001); // Fee should still be charged
  }

  @Test
  void testBalanceNeverBelowMinimum() {
    checkingAccount.deposit(100.00);
    assertTrue(checkingAccount.withdraw(50.00)); // Balance falls to $150
    assertTrue(checkingAccount.withdraw(40.00)); // Balance falls to $110
    checkingAccount.performMonthlyMaintenance();
    assertEquals(110.00, checkingAccount.getBalance(), 0.001); // No fee should be charged
  }

  @Test
  void testBalanceBelowMinimumMultipleTimes() {
    assertTrue(checkingAccount.withdraw(50.00)); // Balance falls to $50
    checkingAccount.deposit(100.00); // Balance back to $150
    assertTrue(checkingAccount.withdraw(60.00)); // Balance falls to $90
    checkingAccount.performMonthlyMaintenance();
    assertEquals(85.00, checkingAccount.getBalance(), 0.001); // Fee should be charged once
  }

  @Test
  void testBalanceBelowMinimumResetAfterMaintenance() {
    assertTrue(checkingAccount.withdraw(50.00)); // Balance falls to $50
    checkingAccount.performMonthlyMaintenance();
    assertEquals(45.00, checkingAccount.getBalance(), 0.001); // Fee charged

    checkingAccount.deposit(60.00); // Balance now $105
    checkingAccount.performMonthlyMaintenance();
    assertEquals(105.00, checkingAccount.getBalance(), 0.001); // No fee charged in new cycle
  }

  @Test
  void testDepositToAvoidMinimum() {
    assertTrue(checkingAccount.withdraw(80.00)); // Balance falls to $20
    assertTrue(checkingAccount.withdraw(10.00)); // Balance falls to $10
    checkingAccount.deposit(10.00); // Balance back to $20
    checkingAccount.performMonthlyMaintenance();
    assertEquals(15.00, checkingAccount.getBalance(), 0.001); // Fee should still be charged
  }

  @Test
  void testPerformMonthlyMaintenance() {
    checkingAccount.performMonthlyMaintenance();
    assertEquals(100.00, checkingAccount.getBalance(), 0.001); // No fee

    checkingAccount.withdraw(1.00);
    checkingAccount.performMonthlyMaintenance();
    assertEquals(94.00, checkingAccount.getBalance(), 0.001); // 99 - 5 (fee) = 94

    checkingAccount.deposit(10.00);
    checkingAccount.performMonthlyMaintenance();
    assertEquals(104.00, checkingAccount.getBalance(), 0.001); // No fee
  }

  @Test
  void testToString() {
    assertEquals("$100.00", checkingAccount.toString());
    checkingAccount.deposit(0.50);
    assertEquals("$100.50", checkingAccount.toString());
  }
}