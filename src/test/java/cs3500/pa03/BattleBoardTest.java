package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test methods for board
 */
class BattleBoardTest {
  BattleBoard board;
  Carrier wide;
  Carrier slim;

  /**
   * Set up board for testing
   */
  @BeforeEach
  void setUp() {
    slim = new Carrier(new Coord(0, 0));
    wide = new Carrier(new Coord(0, 0));
    wide.rotate();
    board = new BattleBoard(6, 6);
  }

  /**
   * Test checking for other ship blockage
   */
  @Test
  void blockedByOtherShips() {
    board.board[0][1].presentShip = slim;
    assertTrue(board.blockedByOtherShips(wide, new Coord(0, 0)));
    assertFalse(board.blockedByOtherShips(wide, new Coord(0, 1)));
  }

  /**
   * Test checking board size placement
   */
  @Test
  void isProperPlacement() {
    for(int posn = 0; posn < 6; posn++) {
      assertTrue(board.isProperPlacement(wide, new Coord(0, posn)));

      assertTrue(board.isProperPlacement(slim, new Coord(posn, 0)));
    }
    assertFalse(board.isProperPlacement(wide, new Coord(1, 0)));
    assertFalse(board.isProperPlacement(slim, new Coord(0, 1)));
  }

  /**
   * Test boat placement checking
   */
  @Test
  void hasRoomForShip() {
    for(int posn = 0; posn < 6; posn++) {
      assertTrue(board.hasRoomForShip(wide, new Coord(0, posn)));
      assertTrue(board.hasRoomForShip(slim, new Coord(posn, 0)));
    }
  }

  /**
   * Test manual boat placement
   */
  @Test
  void testBoatPlacement() {
    assertTrue(this.board.tryPlaceShip(wide, new Coord(0,0)));
    for(int pos = 0; pos < 6; pos++) {
      assertEquals('C', this.board.board[0][pos].presentShip.getShipSymbol());
    }
    assertFalse(this.board.tryPlaceShip(slim, new Coord(0, 0)));
    assertNull(this.board.board[1][0].presentShip);
    this.board.tryPlaceShip(new Destroyer(new Coord(0, 1)), new Coord(0, 1));
    for(int pos = 1; pos < 5; pos++) {
      assertEquals('D', this.board.board[pos][0].presentShip.getShipSymbol());
    }
  }
  /**
   * Test random boat placement
   */
  @Test
  void testRandomBoatPlacement() {
    this.board.placeShipRandom(wide, new Random(0));

    for (int curCol = 0; curCol < 6; curCol++) {
      assertEquals( "C", board.board[2][curCol].toString());
    }
    assertEquals("(0, 2)", wide.location.toString());

    this.board.placeShipRandom(slim, new Random(0));
    //System.out.println(this.board);
    for (int curCol = 0; curCol < 6; curCol++) {
      assertEquals( "C", board.board[1][curCol].toString());
    }
    assertEquals("(0, 1)", slim.location.toString());
  }

  /**
   * Test String representations
   */
  @Test
  void testBoardToString() {
    String expectedEmptyBoard =
        """
        . . . . . .
        . . . . . .
        . . . . . .
        . . . . . .
        . . . . . .
        . . . . . .
        """;
    assertEquals(expectedEmptyBoard, board.toString());
  }

  /**
   * Test ship count
   */
  @Test
  void testShipCount() {
    this.board.placeShipRandom(wide, new Random(0));
    this.board.placeShipRandom(wide, new Random(0));
    assertEquals(2, this.board.getNumActiveShips());
    this.board.presentShips.get(0).destroyed = true;
    assertEquals(0, this.board.getNumActiveShips());
  }

  /**
   * Test checking of coordinates
   */
  @Test
  void testCheckCoords() {
    assertTrue(this.board.checkValidCoord(new Coord(0, 4)));
    assertFalse(this.board.checkValidCoord(new Coord(6, 10)));
  }

  /**
   * Test checking presence of ship
   */
  @Test
  void hasShipHere() {
    this.board.tryPlaceShip(wide, new Coord(0, 0));
    for (int col = 0 ; col < 6; col++) {
      assertTrue(board.hasShipHere(new Coord(col, 0)));
      assertFalse(board.hasShipHere(new Coord(col, 1)));
    }
  }

  /**
   * Test checking if ship is destroyed
   */
  @Test
  void testDestroyCheck() {
    this.board.tryPlaceShip(wide, new Coord(0, 0));
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][0].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][1].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][2].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][3].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][4].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertFalse(wide.destroyed);
    this.board.board[0][5].firedAt = true;
    this.board.shipSinkCheck(wide);
    assertTrue(wide.destroyed);
  }

  /**
   * Test showing sent shots
   */
  @Test
  void testShowSentShots() {
    ArrayList<Coord> sentShots = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(1, 1),
        new Coord(2, 2),
        new Coord(3, 3)));
    ArrayList<Coord> missedShots = new ArrayList<>(Arrays.asList(new Coord(0, 1),
        new Coord(1, 2),
        new Coord(2, 3),
        new Coord(3, 4)));
    String expectedOutput =
        """
        X . . . . .
        - X . . . .
        . - X . . .
        . . - X . .
        . . . - . .
        . . . . . .
        """;
    String actualOutput = BattleBoard.showSentShots(6, 6, sentShots, missedShots);
    assertEquals(expectedOutput, actualOutput);
  }

  /**
   * Test printing board from a static context
   */
  @Test
  void testStaticPrinting() {
    wide.location = new Coord(2, 0);
    slim.location = new Coord(0, 1);

    ArrayList<Coord> testShots = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(0, 1),
        new Coord (0, 6),
        new Coord(4, 4),
        new Coord(6, 0)));

    ArrayList<Ship> testList = new ArrayList<>(Arrays.asList(wide, slim));

    String expectedBoard =
        """
        . . C C C C X C . .
        X . . . . . . . . .
        C . . . . . . . . .
        C . . . . . . . . .
        C . . . . . . . . .
        C . . . . . . . . .
        X . . . . . . . . .
        . . . . . . . . . .
        . . . . . . . . . .
        . . . . . . . . . .
        """;
    assertEquals(expectedBoard,
        BattleBoard.toStringListShips(10, 10, testList, testShots));
  }
}