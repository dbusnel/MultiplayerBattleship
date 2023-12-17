package controller;

import cs3500.pa03.BattleBoard;
import cs3500.pa03.Battleship;
import cs3500.pa03.Carrier;
import cs3500.pa03.Coord;
import cs3500.pa03.Destroyer;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import cs3500.pa03.Submarine;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import view.BattleView;

/**
 * Represents a manual player that takes manual input
 */
public class ManualPlayer implements Player {
  private final String name;
  public BattleBoard board;
  private Random randGen;
  private BattleView view;
  private ControllerInputHandler input;

  /**
   * Construct a ManualPlayer Object
   */
  public ManualPlayer(String name, BattleBoard board) {
    this.name = name;
    this.board = board;
    this.randGen = new Random();
  }

  /**
   * Construct a ManualPlayer Object with just a name
   */
  public ManualPlayer(String name) {
    this.name = name;
    this.view = new BattleView(new PrintStream(System.out));
    this.input = new ControllerInputHandler(new InputStreamReader(System.in));
    this.randGen = new Random();
  }

  /**
   * Construct a ManualPlayer Object connected to view module
   */
  public ManualPlayer(String name, BattleBoard board,
                      BattleView view, ControllerInputHandler input) {
    this.name = name;
    this.board = board;
    this.randGen = new Random();
    this.view = view;
    this.input = input;
  }

  /**
   * Construct a ManualPlayer Object with a given random generator
   */
  public ManualPlayer(String name, BattleBoard board, Random randGen) {
    this.name = name;
    this.board = board;
    this.randGen = randGen;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new BattleBoard(width, height);
    ArrayList<Ship> outputShips = new ArrayList<>();
    for (ShipType type : specifications.keySet()) {
      for (int numShips = 0; numShips < specifications.get(type); numShips++) {
        Ship toCreate = switch (type) {
          case CARRIER -> new Carrier();
          case BATTLESHIP -> new Battleship();
          case DESTROYER -> new Destroyer();
          case SUBMARINE -> new Submarine();
        };
        //enumerated ship types
        this.board.placeShipRandom(toCreate, randGen);
        outputShips.add(toCreate);
      }
    }
    return outputShips;
  }

  /**
   * Update the random seed
   *
   * @param randGen Random
   */
  public void setRandGen(Random randGen) {
    this.randGen = randGen;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int numShots = this.board.getNumActiveShips();
    boolean successfulInput = true;
    ArrayList<Coord> outputShots;

    do {
      outputShots = new ArrayList<>();
      view.log("Input " + numShots + " shots:\n");
      for (int shot = 0; shot < numShots; shot++) {
        view.log("Enter coordinates: ");
        String current = input.readLine();
        if (Coord.validCoordString(current)) {
          Coord currentCoord = Coord.makeCoord(current);
          if (this.board.checkValidCoord(currentCoord)) {
            outputShots.add(currentCoord);
          } else {
            //System.out.println("Coordinate doesn't fit in board");
            successfulInput = false;
          }
        } else {
          //System.out.println("Invalid coordinate input");
          successfulInput = false;
        }
      }
    } while (!successfulInput);

    return outputShots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> successfulHits = new ArrayList<>();
    for (Coord incomingHitCoord : opponentShotsOnBoard) {
      if (this.board.hasShipHere(incomingHitCoord)) {
        successfulHits.add(incomingHitCoord);
        this.board.board[incomingHitCoord.ypos][incomingHitCoord.xpos].firedAt = true;
      }
    }
    for (Ship s : this.board.presentShips) {
      this.board.shipSinkCheck(s);
    }
    return successfulHits;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    view.log("Successful hits:\n");
    if (shotsThatHitOpponentShips.size() == 0) {
      view.log("none :(\n");
    } else {
      for (Coord hit : shotsThatHitOpponentShips) {
        view.log(hit.toString() + "\n");
      }
    }
  }

  /**
   * Set the view of the player
   *
   * @param view The view to set
   */
  public void setView(BattleView view) {
    this.view = view;
  }

  /**
   * Set the input of the player
   *
   * @param input The input to set
   */
  public void setInput(ControllerInputHandler input) {
    this.input = input;
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    switch (result) {
      case WIN -> view.log("Victory! ");
      case LOSE -> view.log("Defeat. ");
      default -> view.log("It was a tie... ");
    }
    view.log(reason + "\n");
  }
}
