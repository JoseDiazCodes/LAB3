package bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IAccountTest {

  private IAccount savingsAccount;
  private IAccount checkingAccount;

  @BeforeEach
  void setUp() {
    savingsAccount = new SavingsAccount(100.00);
    checkingAccount = new CheckingAccount(100.00);
  }

  @Test
  void testDeposit() {
    savingsAccount.deposit(50.00);
    assertEquals(150.00, savingsAccount.getBalance(), 0.001);

    checkingAccount.deposit(75.00);
    assertEquals(175.00, checkingAccount.getBalance(), 0.001);

    assertThrows(IllegalArgumentException.class, () -> savingsAccount.deposit(-10.00));
    assertThrows(IllegalArgumentException.class, () -> checkingAccount.deposit(-10.00));
  }

  @Test
  void testWithdraw() {
    assertTrue(savingsAccount.withdraw(50.00));
    assertEquals(50.00, savingsAccount.getBalance(), 0.001);

    assertTrue(checkingAccount.withdraw(75.00));
    assertEquals(25.00, checkingAccount.getBalance(), 0.001);

    assertFalse(savingsAccount.withdraw(100.00));
    assertFalse(checkingAccount.withdraw(50.00));

    assertFalse(savingsAccount.withdraw(-10.00));
    assertFalse(checkingAccount.withdraw(-10.00));
  }

  @Test
  void testGetBalance() {
    assertEquals(100.00, savingsAccount.getBalance(), 0.001);
    assertEquals(100.00, checkingAccount.getBalance(), 0.001);

    savingsAccount.deposit(50.00);
    checkingAccount.withdraw(25.00);

    assertEquals(150.00, savingsAccount.getBalance(), 0.001);
    assertEquals(75.00, checkingAccount.getBalance(), 0.001);
  }
}