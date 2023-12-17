package cs3500.pa03;

/**
 * Represents a BattleSalvo destroyer
 */
public class Destroyer extends Ship {
  /**
   * Create a carrier of length 4
   *
   * @param location The coordinate of the top left point of the ship
   */
  public Destroyer(Coord location) {
    super();
    this.location = location;
    this.length = 4;
  }

  /**
   * Create destroyer without placing coordinate
   */
  public Destroyer() {
    super();
    this.length = 4;
  }

  /**
   * Return the representative character of the ship 'D'
   *
   * @return char
   */
  public char getShipSymbol() {
    return 'D';
  }
}
