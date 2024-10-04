package bank;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  void testDepositToAvoidMinimum() {
    assertTrue(checkingAccount.withdraw(80.00)); // Balance falls to $20
    assertTrue(checkingAccount.withdraw(10.00)); // Balance falls to $10
    checkingAccount.deposit(10.00); // Balance back to $20
    checkingAccount.performMonthlyMaintenance();
    assertEquals(15.00, checkingAccount.getBalance(), 0.001); // Fee should still be charged
  }

  @Test
  void testToString() {
    assertEquals("$100.00", checkingAccount.toString());
    checkingAccount.deposit(0.50);
    assertEquals("$100.50", checkingAccount.toString());
  }
}
