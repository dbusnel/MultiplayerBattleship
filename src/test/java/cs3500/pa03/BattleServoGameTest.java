package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.*;

import controller.AutoPlayer;
import controller.ControllerInputHandler;
import controller.Player;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BattleView;

/**
 * Test the BattleServo game
 */
class BattleServoGameTest {

  StringReader readStrings;
  ControllerInputHandler input;
  StringBuilder testOutput;
  BattleView view;
  BattleServoGame game;
  Player player1;
  Player player2;

  /**
   * Complete setup for testing
   */
  @BeforeEach
  void setUp() {
    this.readStrings = new StringReader("");
    this.input = new ControllerInputHandler(readStrings);
    this.testOutput = new StringBuilder();
    this.view = new BattleView(testOutput);
    this.player1 = new AutoPlayer();
    this.player2 = new AutoPlayer();
    this.game = new BattleServoGame(player1, player2, view, input);
  }

  /**
   * Do a single turn
   */
  @Test
  void doServoTurn() {

    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    readStrings = new StringReader("");
    testOutput = new StringBuilder();
    game.view = new BattleView(testOutput);
    game.input = new ControllerInputHandler(readStrings);
    game.specifications = specs;
    game.boardHeight = 6;
    game.boardWidth = 6;

    Destroyer test = new Destroyer();
    test.location = new Coord(0, 0);
    game.p1Ships = new ArrayList<>(Arrays.asList(test));
    game.p2Ships= new ArrayList<>(Arrays.asList(test));
    game.p1Ships = player1.setup(game.boardHeight, game.boardWidth, specs);
    game.p2Ships = player2.setup(game.boardHeight, game.boardWidth, specs);

    readStrings = new StringReader("");
    testOutput = new StringBuilder();
    game.view = new BattleView(testOutput);
    game.input = new ControllerInputHandler(readStrings);
    //this.getBoardSize();

    this.game.doServoTurn();

    //this.readStrings = new StringReader("6 6\n1 1 1 1\n");
    //this.game.input = new ControllerInputHandler(this.readStrings);
    //this.game.playGame();
  }

  /**
   * Test getting the board size
   */
  @Test
  void getBoardSize() {
    readStrings = new StringReader("1 20\n7 10\n");
    testOutput = new StringBuilder();
    game.view = new BattleView(testOutput);
    game.input = new ControllerInputHandler(readStrings);
    String expectedConsoleOutput = """
        Please enter a valid height and width (in the format H W):
        Invalid dimensions. Height and width should be in range [6, 15], inclusive. Try again:
        """;
    Coord expectedOutput = new Coord(10, 7);
    Coord actualOutput = game.getBoardSize();
    assertEquals(expectedOutput.toString(), actualOutput.toString());
    assertEquals(expectedConsoleOutput, game.view.output.toString());
    assertEquals(readStrings.toString(), game.input.input.toString());
  }

  /**
   * Test getting the number of ships
   */
  @Test
  void getNumShips() {

    Map<ShipType, Integer> expectedSpecs = new HashMap<>();
    expectedSpecs.put(ShipType.CARRIER, 1);
    expectedSpecs.put(ShipType.BATTLESHIP, 2);
    expectedSpecs.put(ShipType.DESTROYER, 3);
    expectedSpecs.put(ShipType.SUBMARINE, 4);

    readStrings = new StringReader("1 2 2 0\n2 7 2 1\n1 2 3 4\n");
    testOutput = new StringBuilder();
    game.view = new BattleView(testOutput);
    game.input = new ControllerInputHandler(readStrings);
    String expectedConsoleOutput = """
        Enter number of ships in the format [Carrier, Battleship, Destroyer, Submarine]:
        Invalid fleet size. Remember your fleet size must not exceed 10
        Enter number of ships in the format [Carrier, Battleship, Destroyer, Submarine]:
        Invalid fleet size. Remember your fleet size must not exceed 10
        Enter number of ships in the format [Carrier, Battleship, Destroyer, Submarine]:
        """;


    Map<ShipType, Integer> actualSpecs = game.getNumShips(10);

    assertEquals(expectedSpecs.get(ShipType.CARRIER), actualSpecs.get(ShipType.CARRIER));
    assertEquals(expectedSpecs.get(ShipType.BATTLESHIP), actualSpecs.get(ShipType.BATTLESHIP));
    assertEquals(expectedSpecs.get(ShipType.DESTROYER), actualSpecs.get(ShipType.DESTROYER));
    assertEquals(expectedSpecs.get(ShipType.SUBMARINE), actualSpecs.get(ShipType.SUBMARINE));

    assertEquals(expectedConsoleOutput, game.view.output.toString());
    assertEquals(readStrings.toString(), game.input.input.toString());
  }

}