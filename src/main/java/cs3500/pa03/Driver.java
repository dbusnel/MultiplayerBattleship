package cs3500.pa03;

import controller.AutoPlayer;
import controller.ControllerInputHandler;
import controller.ManualPlayer;
import controller.Player;
import cs3500.pa04.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import view.BattleView;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      multiPlayer(args);
    } else if (args.length == 0) {
      singlePlayer();
    } else {
      throw new IllegalStateException("Invalid arguments. Please enter a host address and port or"
          + "no arguments at all.");
    }
  }

  /**
   * Run a multiplayer game of BattleServo
   */
  public static void multiPlayer(String[] args) {
    Player p1 = new AutoPlayer();
    String host = args[0];
    String port = args[1];
    ProxyController game;
    try {
      game = new ProxyController(p1, new Socket(host, Integer.parseInt(port)));
    } catch (UnknownHostException e) {
      throw new IllegalArgumentException("Host for game could not be resolved.");
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid port for game. Should be an integer");
    }
    game.playGame();

  }

  /**
   * Run a single player game of BattleServo
   */
  public static void singlePlayer() {
    Player p1 = new ManualPlayer("David");
    Player p2 = new AutoPlayer();
    BattleServoGame game = new BattleServoGame(p1, p2, new BattleView(new PrintStream(System.out)),
        new ControllerInputHandler(new InputStreamReader(System.in)));
    game.playGame();
  }
}