package controller;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.BattleBoard;
import cs3500.pa03.Carrier;
import cs3500.pa03.Coord;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for AI Player
 */
class AutoPlayerTest {

  AutoPlayer testPlayer;
  BattleBoard testBoard;
  Map<ShipType, Integer> specifications;

  /**
   * Set up for testing
   */
  @BeforeEach
  void setUp() {
    testBoard = new BattleBoard(6, 6);
    testPlayer = new AutoPlayer();
    testPlayer.setRandGen(new Random(0));
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Test getting name
   */
  @Test
  void name() {assertEquals("Smartypants", testPlayer.name());}

  /**
   * Test ai setup
   */
  @Test
  void setup() {
    List<Ship> actualOutput = testPlayer.setup(6, 6, specifications);


    for (Ship s : actualOutput) {
      //System.out.println(s.location.toString() + " " + s.getShipSymbol());
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
    //System.out.println(this.testBoard.toString());
  }

  /**
   * Test automatic ship firing
   */
  @Test
  void takeShots() {
    testPlayer.setup(6, 6, specifications);
    List<Coord> actualShots = testPlayer.takeShots();
    StringBuilder stringOutput = new StringBuilder();
    for(Coord c : actualShots) {
      stringOutput.append(c.toString());
    }

    String expectedShots = "(5, 2)(4, 3)(4, 4)(4, 0)";
    //assertEquals(stringOutput.toString(), expectedShots);
  }

  /**
   * Test damage report
   */
  @Test
  void reportDamage() {
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

  /**
   * Test ending the game
   */
  @Test
  void endGame() { }
}