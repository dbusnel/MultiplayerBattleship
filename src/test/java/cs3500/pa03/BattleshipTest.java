package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing class for Battleship ship
 */
class BattleshipTest {
  Battleship b1 = new Battleship(new Coord(0, 0));

  /**
   * Ensure length, width, and rotation work as intended
   */
  @Test
  public void testBattleshipAttributes() {
    assertEquals(b1.width, 1);
    assertEquals(b1.length, 5);
    b1.rotate();
    assertEquals(b1.width, 5);
    assertEquals(b1.length, 1);
    assertEquals(b1.getShipSymbol(), 'B');
  }
}