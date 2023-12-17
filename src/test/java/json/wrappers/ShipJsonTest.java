package json.wrappers;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.Coord;
import cs3500.pa04.json.wrappers.CoordJson;
import cs3500.pa04.json.wrappers.ShipJson;
import org.junit.jupiter.api.Test;

/**
 * Test JSON wrapper class for Ship class
 */
class ShipJsonTest {
  /**
   * Test getting record as a ship
   */
  @Test
  void getAsShip() {

    ShipJson test = new ShipJson(new CoordJson(0, 0), 6, "HORIZONTAL");
    Coord currentPosn = new Coord(0, 0);

    assertEquals(test.convertToShip().getShipSymbol(), 'C');
    assertEquals(test.convertToShip().width, 6);
    assertEquals(test.convertToShip().location, currentPosn);

    test = new ShipJson(new CoordJson(2, 5), 3, "VERTICAL");
    currentPosn = new Coord(2, 5);

    assertEquals(test.convertToShip().getShipSymbol(), 'S');
    assertEquals(test.convertToShip().length, 3);
    assertEquals(test.convertToShip().location, currentPosn);

  }
}