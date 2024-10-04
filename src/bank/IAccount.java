package bank;

/**
 * This interface represents a bank account. It is the super-type for
 * any other type of traditional financial account a bank might offer.
 */
public interface IAccount {

  /**
   * Deposits the specified amount into the account.
   *
   * @param amount The amount of money to deposit.
   * @throws IllegalArgumentException if the amount is negative.
   */
  void deposit(double amount);

  /**
   * Withdraws the specified amount from the account if possible.
   *
   * @param amount The amount of money to withdraw.
   * @return true if the withdrawal was successful, false otherwise.
   *         The withdrawal might fail if the amount is negative, zero,
   *         or greater than the current balance.
   */
  boolean withdraw(double amount);

  /**
   * Retrieves the current balance of the account.
   *
   * @return The current balance of the account.
   */
  double getBalance();

  /**
   * Performs monthly maintenance on the account.
   * This may include applying fees, resetting transaction counters,
   * or any other monthly account maintenance tasks.
   */
  void performMonthlyMaintenance();
}