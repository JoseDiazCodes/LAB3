package bank;

/**
 * Abstract class representing a generic bank account.
 * This class implements the IAccount interface and provides
 * common functionality for different types of bank accounts.
 */
abstract class AbstractAccount implements IAccount {
  protected double starterAmount;
  protected double balance;
  protected static final double MIN_STARTING_BALANCE = BankCriteria.MIN_STARTING_BALANCE.getValue();
  protected static final int SAVINGS_MAX_WITHDRAWALS =
          BankCriteria.SAVINGS_MAX_WITHDRAWL.getIntValue();
  protected static final double SAVINGS_PENALTY =
          BankCriteria.SAVINGS_MAX_WITHDRAWL_PENALTY.getValue();
  protected static final double CHECKING_MIN_BALANCE =
          BankCriteria.CHECKING_MONTHLY_MIN_BALANCE.getValue();
  protected static final double CHECKING_PENALTY =
          BankCriteria.CHECKING_MONTHLY_MIN_PENALTY.getValue();
  protected  static final double MIN_DEPOSIT =
          BankCriteria.MIN_DEPOSIT.getValue();

  /**
   * Constructs a new AbstractAccount with the given starter amount.
   *
   * @param starterAmount The initial amount to deposit in the account.
   * @throws IllegalArgumentException if the starter amount is less than $0.01.
   */
  protected AbstractAccount(double starterAmount) throws IllegalArgumentException {
    if (starterAmount < MIN_STARTING_BALANCE) {
      throw new IllegalArgumentException(
              "Starting amount cannot be negative and/or less than $0.01");
    }
    this.starterAmount = starterAmount;
    this.balance = starterAmount;
  }

  /**
   * Deposits the specified amount into the account.
   *
   * @param amount The amount to deposit.
   * @throws IllegalArgumentException if the amount is negative.
   */
  @Override
  public void deposit(double amount) throws IllegalArgumentException {
    if (amount < MIN_DEPOSIT) {
      throw new IllegalArgumentException("Can't deposit a negative amount");
    }
    balance = balance + amount;
  }

  /**
   * Withdraws the specified amount from the account if possible.
   *
   * @param amount The amount to withdraw.
   * @return true if the withdrawal was successful, false otherwise.
   */
  @Override
  public boolean withdraw(double amount) {
    if (amount > balance || amount <= 0) {
      return false;
    }
    balance = balance - amount;
    return true;
  }

  /**
   * Returns the current balance of the account.
   *
   * @return The current balance.
   */
  @Override
  public double getBalance() {
    return balance;
  }

  /**
   * Performs monthly maintenance on the account.
   * This method is abstract and should be implemented by subclasses.
   */
  @Override
  public abstract void performMonthlyMaintenance();

  /**
   * Returns a string representation of the account balance.
   *
   * @return A string representing the account balance in dollars and cents.
   */
  @Override
  public String toString() {
    return String.format("$%.2f", balance);
  }
}