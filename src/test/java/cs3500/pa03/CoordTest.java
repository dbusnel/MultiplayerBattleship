package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Class for testing coordinate methods
 */
class CoordTest {

  /**
   * Test String conversion methods
   */
  @Test
  void testToString() {
    Coord c1 = new Coord(0, 0);
    assertEquals(c1.toString(), "(0, 0)");
  }

  /**
   * Test checking for valid coordinate
   */
  @Test
  void validCoordString() {
    String test = "2 4";
    assertTrue(Coord.validCoordString(test));
    test = "a 5";
    assertFalse(Coord.validCoordString(test));
    test = "2, 4";
    assertFalse(Coord.validCoordString(test));
    test = "24";
    assertFalse(Coord.validCoordString(test));
  }

  /**
   * Test checking single number string
   */
  @Test
  void checkStringSingleNumber() {
    assertTrue(Coord.checkStringSingleNumber("3"));
    assertFalse(Coord.checkStringSingleNumber("a"));
    assertFalse(Coord.checkStringSingleNumber("a348945"));
  }

  /**
   * Test String coordinate conversion
   */
  @Test
  void testStringConversion() {
    assertEquals("(5, 2)", Coord.makeCoord("5 2").toString());
  }

  /**
   * Test generation of random coordinate within a given bound
   */
  @Test
  void genRandomCoordWithinBounds() {
    Coord random = Coord.genRandomCoordWithinBounds(new Random(0), 6, 6);
    assertEquals("(0, 4)", random.toString());
  }
}