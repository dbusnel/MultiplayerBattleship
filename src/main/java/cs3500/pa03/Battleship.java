package cs3500.pa03;

/**
 * Represents a BattleSalvo battleship
 */
public class Battleship extends Ship {
  /**
   * Create a submarine of length 5
   *
   * @param location The coordinate of the top left point of the ship
   */
  public Battleship(Coord location) {
    super();
    this.location = location;
    this.length = 5;
  }

  /**
   * Create battleship without placing coordinate
   */
  public Battleship() {
    super();
    this.length = 5;
  }

  /**
   * Return the representative character of the ship 'B'
   *
   * @return char
   */
  public char getShipSymbol() {
    return 'B';
  }
}
