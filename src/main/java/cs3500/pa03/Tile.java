package cs3500.pa03;

/**
 * Represents a tile on the BattleServo board that is either empty
 * or houses a Ship
 */
public class Tile {
  public Coord position;
  public Ship presentShip;
  public boolean firedAt;

  /**
   * Construct a tile with a present ship
   */
  public Tile(Coord position, Ship presentShip) {
    this.position = position;
    this.presentShip = presentShip;
    this.firedAt = false;
  }

  /**
   * Construct a tile with no ship
   */
  public Tile(Coord position) {
    this.position = position;
  }

  /**
   * Returns whether a ship is at the tile
   */
  public boolean hasShipHere() {
    return !(this.presentShip == null);
  }

  /**
   * Returns the ship (or the lack thereof) present at the tile
   */
  public Ship getShip() {
    return this.presentShip;
  }

  /**
   * Convert the tile to a single character
   *
   * @return String
   */
  @Override
  public String toString() {
    if (this.firedAt) {
      if (this.presentShip != null) {
        return "X";
      } else {
        return "-";
      }
    } else if (this.presentShip != null) {
      return String.valueOf(this.presentShip.getShipSymbol());
    } else {
      return ".";
    }
  }

}
