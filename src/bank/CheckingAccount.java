package bank;

/**
 * Represents a checking account with specific maintenance fee rules.
 * This class extends AbstractAccount and implements checking account-specific behavior.
 */
public class CheckingAccount extends AbstractAccount {
  private boolean balanceFellBelowMinimum;

  /**
   * Constructs a new CheckingAccount with the given starter amount.
   *
   * @param starterAmount The initial amount to deposit in the account.
   * @throws IllegalArgumentException if the starter amount is less than the minimum required.
   */
  public CheckingAccount(double starterAmount) throws IllegalArgumentException {
    super(starterAmount);
    this.balanceFellBelowMinimum = starterAmount < CHECKING_MIN_BALANCE;
  }

  /**
   * Withdraws the specified amount from the account if possible.
   * Also checks if the balance falls below the minimum after withdrawal.
   *
   * @param amount The amount to withdraw.
   * @return true if the withdrawal was successful, false otherwise.
   */
  @Override
  public boolean withdraw(double amount) {
    boolean withdrawalSuccessful = super.withdraw(amount);
    if (withdrawalSuccessful && balance < CHECKING_MIN_BALANCE) {
      balanceFellBelowMinimum = true;
    }
    return withdrawalSuccessful;
  }

  /**
   * Deposits the specified amount into the account.
   * Overridden to check if the balance is below minimum before deposit.
   *
   * @param amount The amount to deposit.
   * @throws IllegalArgumentException if the amount is negative.
   */
  @Override
  public void deposit(double amount) throws IllegalArgumentException {
    if (balance < CHECKING_MIN_BALANCE) {
      balanceFellBelowMinimum = true;
    }
    super.deposit(amount);
  }

  /**
   * Performs monthly maintenance on the checking account.
   * If the balance fell below the minimum required balance at any time during the month,
   * a single maintenance fee of $5 is charged.
   */
  @Override
  public void performMonthlyMaintenance() {
    if (balanceFellBelowMinimum) {
      balance -= CHECKING_PENALTY;
    }
    balanceFellBelowMinimum = false; // Reset for the next month
  }
}