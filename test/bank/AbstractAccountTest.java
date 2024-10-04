package bank;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AbstractAccountTest {

  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new SavingsAccount(0.00));
    assertThrows(IllegalArgumentException.class, () -> new SavingsAccount(-10.00));
    assertDoesNotThrow(() -> new SavingsAccount(0.01));
  }

  @Test
  void testToString() {
    AbstractAccount account = new SavingsAccount(100.00);
    assertEquals("$100.00", account.toString());
  }
}