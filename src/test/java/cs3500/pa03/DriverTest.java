package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test Driver class
 */
class DriverTest {

  /**
   * Test that an exception is thrown if invalid args are passed
   */
  @Test
  void main() {
    assertThrows(IllegalStateException.class, () -> Driver.main(new String[] {"arg1"}));
  }
}