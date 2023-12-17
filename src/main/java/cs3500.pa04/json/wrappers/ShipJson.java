package cs3500.pa04.json.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.Battleship;
import cs3500.pa03.Carrier;
import cs3500.pa03.Destroyer;
import cs3500.pa03.Ship;
import cs3500.pa03.Submarine;

/**
 * Represents a .json Ship object
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson posn,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {

  /**
   * Get the record as a ship object
   */
  public Ship convertToShip() {
    Ship toReturn = switch (this.length) {
      case 6 -> new Carrier(posn.convertToCoord());
      case 5 -> new Battleship(posn.convertToCoord());
      case 4 -> new Destroyer(posn.convertToCoord());
      default -> new Submarine(posn.convertToCoord());
    };
    if (this.direction.equals("HORIZONTAL")) {
      toReturn.rotate();
    }
    return toReturn;
  }
}
