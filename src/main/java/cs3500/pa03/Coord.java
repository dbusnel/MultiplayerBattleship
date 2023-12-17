package cs3500.pa03;

import java.util.Random;

/**
 * Represents a coordinate with an x and a y value
 */
public class Coord {
  public int xpos;
  public int ypos;

  /**
   * Construct a coordinate
   */
  public Coord(int xpos, int ypos) {
    this.xpos = xpos;
    this.ypos = ypos;
  }

  /**
   * Convert to a String
   */
  @Override
  public String toString() {
    return "(" + this.xpos + ", " + this.ypos + ")";
  }

  /**
   * Check if a provided String will produce a valid coordinate in format: num1 num2
   *
   * @param checkCoord the String representation of coordinate to check
   * @return boolean
   */
  public static boolean validCoordString(String checkCoord) {
    String[] split = checkCoord.split(" ");
    if (split.length != 2) {
      return false;
    } else {
      return checkStringSingleNumber(split[0])
          && checkStringSingleNumber(split[1]);
    }
  }

  /**
   * Check if a given String is a single number
   */
  public static boolean checkStringSingleNumber(String s) {
    if (s.length() > 1) {
      return false;
    } else {
      try {
        Integer.parseInt(s);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    }
  }

  /**
   * Make a coord from a given String
   */
  public static Coord makeCoord(String input) {
    if (validCoordString(input)) {
      String[] splitInput = input.split(" ");
      int x = Integer.parseInt(splitInput[0]);
      int y = Integer.parseInt(splitInput[1]);
      return new Coord(x, y);
    } else {
      throw new IllegalArgumentException("Invalid input coordinate");
    }
  }

  /**
   * Generate a random coordinate within the given bounds with the given random seed
   *
   * @param randGen the Random generator to use
   * @param xbound the x limit
   * @param ybound the y limit
   * @return Coord
   */
  public static Coord genRandomCoordWithinBounds(Random randGen, int xbound, int ybound) {
    int x = randGen.nextInt(xbound);
    int y = randGen.nextInt(ybound);
    return new Coord(x, y);
  }

  /**
   * Override equals
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Coord)) {
      return false;
    } else {
      return ((Coord) other).xpos == this.xpos
          && ((Coord) other).ypos == this.ypos;
    }
  }
}
