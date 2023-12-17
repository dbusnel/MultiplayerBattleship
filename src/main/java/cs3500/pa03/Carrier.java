package cs3500.pa03;

/**
 * Represents a BattleServo carrier ship
 */
public class Carrier extends Ship {

  /**
   * Create a carrier of length 6
   *
   * @param location The coordinate of the top left point of the ship
   */
  public Carrier(Coord location) {
    super();
    this.location = location;
    this.length = 6;
  }

  /**
   * Create carrier without placing coordinate
   */
  public Carrier() {
    super();
    this.length = 6;
  }

  /**
   * Return the representative character of the ship 'C'
   *
   * @return char
   */
  public char getShipSymbol() {
    return 'C';
  }

}
