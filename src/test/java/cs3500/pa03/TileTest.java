package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class for testing Tile methods (empty and full)
 */
class TileTest {
  Tile emptyTile = new Tile(new Coord(0, 0));
  Ship testDestroyer = new Destroyer(new Coord(0, 0));
  Tile destroyerTile = new Tile(new Coord(0,0), testDestroyer);

  /**
   * Test checking presence of ships
   */
  @Test
  void hasShipHere() {
    assertFalse(emptyTile.hasShipHere());
    assertTrue(destroyerTile.hasShipHere());
  }

  /**
   * Test getting ships
   */
  @Test
  void getShip() {
    assertNull(emptyTile.getShip());
    assertEquals( 'D', destroyerTile.getShip().getShipSymbol());
  }

  /**
   * Test toString method
   */
  @Test
  void testToString() {
    assertEquals(emptyTile.toString(), ".");
    emptyTile.firedAt = true;
    assertEquals(emptyTile.toString(), "-");
    assertEquals(destroyerTile.toString(), "D");
    destroyerTile.firedAt = true;
    assertEquals(destroyerTile.toString(), "X");
  }
}