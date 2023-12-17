package json.wrappers;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.Coord;
import cs3500.pa04.json.wrappers.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordJsonTest {

  CoordJson test;
  Coord toCompare;

  /**
   * Setup examples for testing
   */
  @BeforeEach
  void setUp() {
    toCompare = new Coord(1, 5);
    test = new CoordJson(1, 5);
  }

  /**
   * Test getting this record as a Coord object
   */
  @Test
  void getAsCoord() {
    assertEquals(test.convertToCoord(), toCompare);
  }
}