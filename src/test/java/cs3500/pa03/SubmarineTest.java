package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing class for Submarine ship
 */
class SubmarineTest {
  Submarine s1 = new Submarine(new Coord(0, 0));

  /**
   * Ensure length, width, and rotation work as intended
   */
  @Test
  public void testSubmarineAttributes() {
    assertEquals(s1.width, 1);
    assertEquals(s1.length, 3);
    s1.rotate();
    assertEquals(s1.width, 3);
    assertEquals(s1.length, 1);
    assertEquals(s1.getShipSymbol(), 'S');
  }
}