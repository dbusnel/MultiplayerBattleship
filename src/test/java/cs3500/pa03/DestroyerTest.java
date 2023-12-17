package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing class for Destroyer ship
 */
class DestroyerTest {
  Destroyer d1 = new Destroyer(new Coord(0, 0));

  /**
   * Ensure length, width, and rotation work as intended
   */
  @Test
  public void testDestroyerAttributes() {
    assertEquals(d1.width, 1);
    assertEquals(d1.length, 4);
    d1.rotate();
    assertEquals(d1.width, 4);
    assertEquals(d1.length, 1);
    assertEquals(d1.getShipSymbol(), 'D');
  }
}