package bank;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

