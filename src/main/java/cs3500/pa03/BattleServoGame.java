package cs3500.pa03;

import controller.ControllerInputHandler;
import controller.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.BattleView;

/**
 * Represents a game of BattleServo with 2 players
 */
public class BattleServoGame {
  public Player player1;
  public int boardWidth;
  public int boardHeight;
  public Player player2;
  public BattleView view;
  public ControllerInputHandler input;
  Map<ShipType, Integer> specifications;
  List<Coord> p1LandedShots = new ArrayList<>();
  List<Coord> p1MissedShots = new ArrayList<>();
  List<Coord> p2LandedShots = new ArrayList<>();
  List<Ship> p1Ships;
  List<Ship> p2Ships;
  int turnNum = 1;

  /**
   * Construct a BattleServoGame with the given players
   */
  public BattleServoGame(Player player1, Player player2, BattleView view,
                         ControllerInputHandler input) {
    this.view = view;
    view.log("Hello! Welcome to the OOD BattleSalvo Game!\n");
    this.input = input;
    this.player1 = player1;
    this.player2 = player2;
  }

  /**
   * Play the game
   */
  public void playGame() {
    this.getBoardSize();
    this.specifications = this.getNumShips(this.boardHeight);

    this.p1Ships = player1.setup(this.boardHeight, this.boardWidth, this.specifications);
    this.p2Ships = player2.setup(this.boardHeight, this.boardWidth, this.specifications);
    while (!this.isGameOver()) {
      this.doServoTurn();
    }

    switch (analyzeGameOutcome()) {

      case WIN -> player1.endGame(analyzeGameOutcome(),
          "Sank all of " + player2.name() + "'s ships!");
      case LOSE -> player1.endGame(analyzeGameOutcome(),
          player2.name() + " sank all of your ships.");
      default -> player1.endGame(analyzeGameOutcome(),
          "All ships were sunken.");
    }

  }

  /**
   * Do a single turn of the BattleServo game
   */
  public void doServoTurn() {
    view.log("Turn " + this.turnNum + ":\n");
    view.log(player2.name() + "'s board:\n");
    view.log(BattleBoard.showSentShots(this.boardHeight,
        this.boardWidth, p1LandedShots, p1MissedShots));
    view.log("\n");
    view.log(player1.name() + "'s board:\n");
    view.log(BattleBoard.toStringListShips(this.boardHeight,
        this.boardWidth, p1Ships, p2LandedShots));

    List<Coord> p1Shots = player1.takeShots();
    List<Coord> p2Shots = player2.takeShots();

    List<Coord> p2HitShots = player1.reportDamage(p2Shots);

    List<Coord> p1HitShots = player2.reportDamage(p1Shots);
    List<Coord> p1MissedShots = new ArrayList<>(p1Shots);
    p1MissedShots.removeAll(p1HitShots);

    this.p1LandedShots.addAll(p1HitShots);
    this.p2LandedShots.addAll(p2HitShots);
    this.p1MissedShots.addAll(p1MissedShots);


  }

  /**
   * Get the size of the BattleServo board
   *
   * @return Coord
   * @throws NumberFormatException if invalid input
   */
  public Coord getBoardSize() throws NumberFormatException {
    int width = 0;
    int height = 0;
    view.log("Please enter a valid height and width (in the format H W):\n");
    boolean successful = false;
    while (!successful) {
      height = Integer.parseInt(input.read());
      width = Integer.parseInt(input.read());
      if (width < 6 || width > 15 || height < 6 || height > 15) {
        view.log("Invalid dimensions. Height and width should be in range [6, 15], inclusive. "
            + "Try again:\n");
      } else {
        successful = true;
      }
    }
    this.boardHeight = height;
    this.boardWidth = width;
    return new Coord(width, height);
  }

  /**
   * Get the number of ships
   *
   * @param maxShips the maximum number of ships
   * @return Specifications map
   * @throws NumberFormatException issue with numbers
   */
  public Map<ShipType, Integer> getNumShips(int maxShips) throws NumberFormatException {
    Map<ShipType, Integer> specs = new HashMap<>();
    boolean successful = false;

    while (!successful) {
      specs = new HashMap<>();
      view.log("Enter number of ships in the format [Carrier, Battleship, Destroyer, "
          + "Submarine]:\n");
      int numCarriers = Integer.parseInt(input.read());
      specs.put(ShipType.CARRIER, numCarriers);
      int numBattleships = Integer.parseInt(input.read());
      specs.put(ShipType.BATTLESHIP, numBattleships);
      int numDestroyers = Integer.parseInt(input.read());
      specs.put(ShipType.DESTROYER, numDestroyers);
      int numSubmarines = Integer.parseInt(input.read());
      specs.put(ShipType.SUBMARINE, numSubmarines);

      int givenFleetSize = numCarriers + numBattleships + numDestroyers + numSubmarines;

      if (givenFleetSize > maxShips || givenFleetSize < 0
          || numBattleships == 0
          || numCarriers == 0
          || numDestroyers == 0
          || numSubmarines == 0) {
        view.log("Invalid fleet size. Remember your fleet size must not exceed " + maxShips
            + "\n");
      } else {
        successful = true;
      }
    }
    return specs;
  }

  /**
   * Check if player 1 loses
   *
   * @return boolean
   */
  public boolean checkP1Defeat() {
    boolean defeat = true;
    for (Ship s : this.p1Ships) {
      defeat &= s.destroyed;
    }
    return defeat;
  }

  /**
   * Check if player 2 is defeated
   *
   * @return boolean
   */
  public boolean checkP2Defeat() {
    boolean defeat = true;
    for (Ship s : this.p2Ships) {
      defeat &= s.destroyed;
    }
    return defeat;
  }

  /**
   * Determine if the game is over
   *
   * @return boolean if game is over or not
   */
  public boolean isGameOver() {
    return this.checkP1Defeat() || this.checkP2Defeat();
  }

  /**
   * Return the game ending status in respect to Player 1
   *
   * @return GameResult
   */
  public GameResult analyzeGameOutcome() {
    boolean p1Defeat = this.checkP1Defeat();
    boolean p2Defeat = this.checkP2Defeat();

    if (p1Defeat && p2Defeat) {
      return GameResult.TIE;
    } else if (p1Defeat) {
      return GameResult.LOSE;
    } else {
      return GameResult.WIN;
    }
  }
}
