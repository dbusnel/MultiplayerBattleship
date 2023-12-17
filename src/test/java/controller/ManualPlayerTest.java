package controller;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.BattleBoard;
import cs3500.pa03.Carrier;
import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BattleView;

/**
 * Test class for manual player
 */
class ManualPlayerTest {
  ManualPlayer testPlayer;
  BattleBoard testBoard;
  Map<ShipType, Integer> specifications;
  StringBuilder testOutput;
  BattleView view;

  /**
   * Setup fields and player object for testing
   */
  @BeforeEach
  void setUp() {
    testBoard = new BattleBoard(6, 6);
    testPlayer = new ManualPlayer("David", testBoard, new Random(0));
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    testOutput = new StringBuilder();
    view = new BattleView(testOutput);
  }

  /**
   * Test name for manual player
   */
  @Test
  void name() {
    assertEquals("David", testPlayer.name());
  }

  /**
   * Test manual player setup
   */
  @Test
  void setup() {
    testPlayer.setRandGen(new Random(0));
    List<Ship> actualOutput = testPlayer.setup(6, 6, specifications);


    for (Ship s : actualOutput) {
      System.out.println(s.location.toString() + " " + s.getShipSymbol());
    }

    /*
    assertEquals('C', actualOutput.get(0).getShipSymbol());
    assertEquals("(0, 2)", actualOutput.get(0).location.toString());
    assertEquals('D', actualOutput.get(1).getShipSymbol());
    assertEquals("(2, 4)", actualOutput.get(1).location.toString());
    assertEquals('B', actualOutput.get(2).getShipSymbol());
    assertEquals("(1, 5)", actualOutput.get(2).location.toString());
    assertEquals('S', actualOutput.get(3).getShipSymbol());
    assertEquals("(1, 3)", actualOutput.get(3).location.toString());
     */
    System.out.println(this.testBoard.toString());
  }

  /**
   * Test ending game for manual player
   */
  @Test
  void endGame() {
    testPlayer.setView(view);
    testPlayer.endGame(GameResult.WIN, "Sank all ships.");
    assertEquals("Victory! Sank all ships.\n", view.output.toString());
    this.setUp();
    testPlayer.setView(view);
    testPlayer.endGame(GameResult.LOSE, "All ships sank.");
    assertEquals("Defeat. All ships sank.\n", view.output.toString());
    this.setUp();
    testPlayer.setView(view);
    testPlayer.endGame(GameResult.TIE, "Lost connection to server.");
    assertEquals("It was a tie... Lost connection to server.\n", view.output.toString());
  }

  /**
   * Test method for obtaining shots
   */
  @Test
  void testTakeShots() {
    String exampleInput = "0 1\n0 2\n0 3\n0 4\n";
    testPlayer.setup(6, 6, specifications);
    ControllerInputHandler testInput = new ControllerInputHandler(new StringReader(exampleInput));
    BattleView testOutput = new BattleView(new StringBuilder());
    testPlayer.setInput(testInput);
    testPlayer.setView(testOutput);
    List<Coord> testCoords = testPlayer.takeShots();
    StringBuilder actualOutput = new StringBuilder();
    String expectedOutput = "(0, 1)(0, 2)(0, 3)(0, 4)";
    for (Coord c : testCoords) {
      actualOutput.append(c.toString());
    }
    assertEquals(expectedOutput, actualOutput.toString());
  }
  /**
   * Test method for reporting shots
   */
  @Test
  void testSuccessfulHits() {
    testPlayer.setView(view);
    ArrayList<Coord> exampleHits = new ArrayList<>(Arrays.asList(
        new Coord(0, 5),
        new Coord(1, 3),
        new Coord(7, 7)));
    testPlayer.successfulHits(exampleHits);
    assertEquals("Successful hits:\n(0, 5)\n(1, 3)\n(7, 7)\n", view.output.toString());
    this.setUp();
    testPlayer.setView(view);
    exampleHits = new ArrayList<>();
    testPlayer.successfulHits(exampleHits);
    assertEquals("Successful hits:\nnone :(\n", view.output.toString());
  }

  /**
   * Test reporting landed shots
   */
  @Test
  void testReportShots() {
    testPlayer.board = new BattleBoard(6, 6);
    testPlayer.board.tryPlaceShip(new Carrier(), new Coord(0, 0));
    List<Coord> successfulShots = testPlayer.reportDamage(new ArrayList<>(Arrays.asList(
        new Coord(0, 0), new Coord(1, 0), new Coord(0, 1))));
    StringBuilder actualOutput = new StringBuilder();
    for (Coord c : successfulShots) {
      actualOutput.append(c.toString());
    }
    String expectedOutput = "(0, 0)(0, 1)";
    assertEquals(expectedOutput, actualOutput.toString());
  }

}