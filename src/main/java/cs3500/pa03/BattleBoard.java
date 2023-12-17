package cs3500.pa03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents a BattleServo board
 */
public class BattleBoard {
  public Tile[][] board;
  public final int width;
  public final int height;
  public ArrayList<Ship> presentShips;

  /**
   * Construct a BattleBoard object with a given width and height
   */
  public BattleBoard(int width, int height) {
    this.height = height;
    this.width = width;
    this.board = new Tile[height][width];
    this.presentShips = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        board[row][col] = new Tile(new Coord(col, row));
      }
    }
  }

  /**
   * Determine if there is room on the board to place the given
   * ship at the given position.
   *
   * @param toPlace Ship to place
   * @param placeAt Location to place Ship
   * @return boolean
   */
  public boolean hasRoomForShip(Ship toPlace, Coord placeAt) {
    return placeAt.xpos + toPlace.width <= this.width
        && placeAt.ypos + toPlace.length <= this.height;
  }

  /**
   * Determine if given Ship is blocked by any other ships
   *
   * @param toPlace Ship to place
   * @param placeAt Location to place ship
   * @return boolean
   */
  public boolean blockedByOtherShips(Ship toPlace, Coord placeAt) {
    boolean result = false;
    for (int curRowPos = placeAt.ypos; curRowPos < placeAt.ypos + toPlace.length; curRowPos++) {
      for (int curColPos = placeAt.xpos; curColPos < placeAt.xpos + toPlace.width; curColPos++) {
        result |= this.board[curRowPos][curColPos].hasShipHere();
      }
    }
    return result;
  }

  /**
   * Determines if a ship can be placed at the given coordinate
   *
   * @param toPlace Ship to place
   * @param placeAt Location to place ship
   * @return boolean
   */
  public boolean isProperPlacement(Ship toPlace, Coord placeAt) {
    if (this.hasRoomForShip(toPlace, placeAt)) {
      return !this.blockedByOtherShips(toPlace, placeAt);
    } else {
      return false;
    }
  }

  /**
   * Place a ship at the given position if it is valid to do so.
   *
   * @param toPlace Ship to place
   * @param location Location to place ship
   * @return boolean if placement was successful
   */
  public boolean tryPlaceShip(Ship toPlace, Coord location) {
    if (isProperPlacement(toPlace, location)) {
      toPlace.location = location;
      this.presentShips.add(toPlace);
      for (int curRow = location.ypos; curRow < location.ypos + toPlace.length; curRow++) {
        for (int curCol = location.xpos; curCol < location.xpos + toPlace.width; curCol++) {
          this.board[curRow][curCol].presentShip = toPlace;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Place the given ship at a random position
   *
   * @param toPlace ship to place
   * @param randGen Random object to use for generation
   */
  public void placeShipRandom(Ship toPlace, Random randGen) {

    //choose a random initial orientation
    if (randGen.nextInt(2) == 0) {
      toPlace.rotate();
    }

    boolean[][] triedPositions = new boolean[this.height][this.width];
    while (true) {
      int randXpos = randGen.nextInt(this.width);
      int randYpos = randGen.nextInt(this.height);
      if (!triedPositions[randYpos][randXpos]) {
        if (this.tryPlaceShip(toPlace, new Coord(randXpos, randYpos))) {
          break;
        } else {
          //check placement for rotated ship
          toPlace.rotate();
          if (this.tryPlaceShip(toPlace, new Coord(randXpos, randYpos))) {
            break;
          }
        }
        toPlace.rotate();
        triedPositions[randYpos][randXpos] = true;
      }
    }
  }

  /**
   * Place all ships from a given list onto board.
   *
   * @param toPlace the ships to place
   */
  public void placeFromList(ArrayList<Ship> toPlace) {
    for (Ship current : toPlace) {
      this.tryPlaceShip(current, current.location);
    }
  }

  /**
   * Get text representation of board
   *
   * @return String
   */
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (Tile[] row : this.board) {
      for (Tile tile : row) {
        output.append(tile.toString());
        output.append(" ");
      }
      //remove trailing space and add a newline
      output.setLength(output.length() - 1);
      output.append("\n");
    }
    return output.toString();
  }

  /**
   * Get text representation of board given list of ships and incoming successful shots
   */
  public static String toStringListShips(int boardHeight, int boardWidth,
                                        List<Ship> ships, List<Coord> successfulShots) {
    String[][] characterRepresentation = new String[boardHeight][boardWidth];
    for (String[] row : characterRepresentation) {
      Arrays.fill(row, ".");
    }
    for (Ship s : ships) {
      int curYpos = s.location.ypos;
      int curXpos = s.location.xpos;
      for (int row = curYpos; row < curYpos + s.length; row++) {
        for (int col = curXpos; col < curXpos + s.width; col++) {
          //System.out.println(s.getShipSymbol() + " " + s.location);
          characterRepresentation[row][col] = String.valueOf(s.getShipSymbol());
        }
      }
    }
    for (Coord landedHit : successfulShots) {
      if (!characterRepresentation[landedHit.ypos][landedHit.xpos].equals(".")) {
        characterRepresentation[landedHit.ypos][landedHit.xpos] = "X";
      }
    }
    StringBuilder adaptedOutput = new StringBuilder();
    for (String[] row : characterRepresentation) {
      for (String pos : row) {
        adaptedOutput.append(pos).append(" ");
      }
      adaptedOutput.setLength(adaptedOutput.length() - 1);
      adaptedOutput.append("\n");
    }
    return adaptedOutput.toString();
  }

  /**
   * Get the number of active ships (number of shots)
   *
   * @return int
   */
  public int getNumActiveShips() {
    int output = 0;
    for (Ship s : this.presentShips) {
      if (!s.destroyed) {
        output++;
      }
    }
    return output;
  }

  /**
   * Check if a given coordinate object is valid
   *
   * @param c The coordinate to check
   * @return boolean
   */
  public boolean checkValidCoord(Coord c) {
    return c.xpos <= this.width && c.ypos <= this.height;
  }

  /**
   * Check if a ship is at the given coordinate
   */
  public boolean hasShipHere(Coord c) {
    if (c.xpos >= this.width || c.ypos >= this.height) {
      throw new IllegalArgumentException("Invalid coordinate");
    } else {
      return this.board[c.ypos][c.xpos].hasShipHere();
    }
  }

  /**
   * Check if a ship has been sunken and update it accordingly
   */
  public void shipSinkCheck(Ship ship) {
    boolean isDestroyed = this.board[ship.location.ypos][ship.location.xpos].firedAt;
    for (int curRow = ship.location.ypos; curRow < ship.location.ypos + ship.length; curRow++) {
      for (int curCol = ship.location.xpos; curCol < ship.location.xpos + ship.width; curCol++) {
        isDestroyed &= this.board[curRow][curCol].firedAt;
      }
    }
    ship.destroyed = isDestroyed;
  }

  /**
   * Show the sent shots that hit and the ones that missed
   */
  public static String showSentShots(int boardHeight, int boardWidth,
                                     List<Coord> hits, List<Coord> misses) {
    String[][] representation = new String[boardHeight][boardWidth];
    for (String[] row : representation) {
      Arrays.fill(row, ".");
    }
    for (Coord c : hits) {
      representation[c.ypos][c.xpos] = "X";
    }
    for (Coord c : misses) {
      representation[c.ypos][c.xpos] = "-";
    }
    StringBuilder output = new StringBuilder();
    for (String[] row : representation) {
      for (String posn : row) {
        output.append(posn).append(" ");
      }
      output.setLength(output.length() - 1);
      output.append("\n");
    }
    return output.toString();
  }
}
