package bank;

/**
 * Represents a savings account with specific withdrawal rules and maintenance fees.
 */
public class SavingsAccount extends AbstractAccount {
  private int withdrawCount;

  /**
   * Constructs a new SavingsAccount with the given starter amount.
   *
   * @param starterAmount The initial amount to deposit in the account.
   * @throws IllegalArgumentException if the starter amount is less than the minimum required.
   */
  public SavingsAccount(double starterAmount) throws IllegalArgumentException {
    super(starterAmount);
    this.withdrawCount = 0;
  }

  /**
   * Withdraws the specified amount from the account if possible.
   *
   * @param amount The amount to withdraw.
   * @return true if the withdrawal was successful, false otherwise.
   */
  @Override
  public boolean withdraw(double amount) {
    if (amount < 0) {
      return false; // Operation fails for negative amounts
    }
    if (super.withdraw(amount)) {
      withdrawCount++;
      return true;
    }
    return false;
  }

  /**
   * Performs monthly maintenance on the account, charging fees if necessary.
   */
  @Override
  public void performMonthlyMaintenance() {
    if (withdrawCount > SAVINGS_MAX_WITHDRAWALS) {
      balance -= SAVINGS_PENALTY;
    }
    withdrawCount = 0; // Reset the withdrawal count for the new month
  }

  /**
   * Gets the current withdrawal count.
   *
   * @return The number of withdrawals made this month.
   */
  public int getWithdrawCount() {
    return withdrawCount;
  }
}