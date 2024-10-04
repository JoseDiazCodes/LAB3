package bank;

/**
 * Enum representing various constants used in banking operations.
 * This enum provides values for minimum balances, maximum withdrawal limits,
 * and associated penalties for different types of bank accounts.
 */
public enum BankCriteria {
  /** Minimum starting balance for any account. */
  MIN_STARTING_BALANCE(0.01),

  /** Minimum deposit amount allowed. */
  MIN_DEPOSIT(0.00),

  /** Maximum number of free withdrawals for a savings account per month. */
  SAVINGS_MAX_WITHDRAWL(6),

  /** Penalty fee for exceeding the maximum withdrawals in a savings account. */
  SAVINGS_MAX_WITHDRAWL_PENALTY(14.00),

  /** Minimum balance required to avoid fees in a checking account. */
  CHECKING_MONTHLY_MIN_BALANCE(100.00),

  /** Penalty fee for falling below the minimum balance in a checking account. */
  CHECKING_MONTHLY_MIN_PENALTY(5.00);

  /** The value associated with each enum constant. */
  private final double value;

  /**
   * Constructs a BankCriteria enum constant with the specified value.
   *
   * @param value The numerical value associated with the criterion.
   */
  BankCriteria(double value) {
    this.value = value;
  }

  /**
   * Returns the value of the bank criterion as a double.
   *
   * @return The value associated with this criterion.
   */
  public double getValue() {
    return value;
  }

  /**
   * Returns the value of the bank criterion as an integer.
   * This method is useful for criteria that represent counts or integer values.
   *
   * @return The integer value associated with this criterion.
   */
  public int getIntValue() {
    return (int) value;
  }
}