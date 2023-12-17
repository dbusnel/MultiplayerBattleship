package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing class for Carrier ship
 */
class CarrierTest {
  Carrier c1 = new Carrier(new Coord(0, 0));

  /**
   * Ensure length, width, and rotation work as intended
   */
  @Test
  public void testCarrierAttributes() {
    assertEquals(c1.width, 1);
    assertEquals(c1.length, 6);
    c1.rotate();
    assertEquals(c1.width, 6);
    assertEquals(c1.length, 1);
    assertEquals(c1.getShipSymbol(), 'C');
  }
}