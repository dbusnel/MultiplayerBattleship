package cs3500.pa03;

/**
 * Represents a single BattleServo ship with a given type, width, and height
 */
public abstract class Ship {
  public int width;
  public int length;
  ShipType type;
  boolean destroyed;
  public Coord location;

  /**
   * Set the width to 1
   */
  public Ship() {
    this.width = 1;
  }

  /**
   * Rotate the ship 90 degrees clockwise
   */
  public void rotate() {
    int tempWidth = this.width;
    this.width = this.length;
    this.length = tempWidth;
  }

  /**
   * Get the rotation of the ship
   *
   * @return the ships rotation
   */
  public String getRotation() {
    if (this.width == 1) {
      return "VERTICAL";
    } else {
      return "HORIZONTAL";
    }
  }

  public abstract char getShipSymbol();

}
