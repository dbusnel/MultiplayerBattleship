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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import view.BattleView;

/**
 * AI Player implementation
 */
public class AutoPlayer implements Player {
  public BattleBoard board;
  private Random randGen;
  boolean[][] firingRecord;
  public BattleView view;

  /**
   * Create a new AI player
   */
  public AutoPlayer() {
    this.randGen = new Random();
    this.view = new BattleView();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Smartypants";
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
    firingRecord = new boolean[height][width];
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

    for (Ship s : this.board.presentShips) {
      this.board.shipSinkCheck(s);
    }

    int numShots = this.board.getNumActiveShips();
    List<Coord> outputShots = new ArrayList<>();

    for (int currentShot = 0; currentShot < numShots; currentShot++) {
      boolean successful = false;
      while (!successful) {
        Coord currentShotCoord = Coord.genRandomCoordWithinBounds(
            this.randGen, this.board.width, this.board.height);
        if (!this.firingRecord[currentShotCoord.ypos][currentShotCoord.xpos]) {
          outputShots.add(currentShotCoord);
          this.firingRecord[currentShotCoord.ypos][currentShotCoord.xpos] = true;
          successful = true;
        }
      }
    }

    return outputShots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
     *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    System.out.println(this.board.toString());
    System.out.println(this.board.getNumActiveShips());
    List<Coord> successfulHits = new ArrayList<>();
    for (Coord incomingHitCoord : opponentShotsOnBoard) {
      if (this.board.hasShipHere(incomingHitCoord)) {
        successfulHits.add(incomingHitCoord);
      }
      this.board.board[incomingHitCoord.ypos][incomingHitCoord.xpos].firedAt = true;
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
    for (Coord c : shotsThatHitOpponentShips) {
      view.log(c.toString() + "\n");
    }
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
    view.log(result.name() + ": " + reason);
  }

  /**
   * Set the view of the AutoPlayer
   *
   * @param v the view to use
   */
  public void setView(BattleView v) {
    this.view = v;
  }
}
