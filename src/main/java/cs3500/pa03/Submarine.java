package cs3500.pa03;

/**
 * Represents a BattleSalvo submarine
 */
public class Submarine extends Ship {
  /**
   * Create a submarine of length 2
   *
   * @param location The coordinate of the top left point of the ship
   */
  public Submarine(Coord location) {
    super();
    this.location = location;
    this.length = 3;
  }

  /**
   * Create submarine without placing coordinate
   */
  public Submarine() {
    super();
    this.length = 3;
  }

  /**
   * Return the representative character of the ship 'S'
   *
   * @return char
   */
  public char getShipSymbol() {
    return 'S';
  }
}
