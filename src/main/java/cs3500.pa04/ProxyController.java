package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ControllerInputHandler;
import controller.Player;
import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.join.ClientEndGameJson;
import cs3500.pa04.json.join.ClientGiveShotsJson;
import cs3500.pa04.json.join.ClientJoinJson;
import cs3500.pa04.json.join.ClientReturnLandedHitsJson;
import cs3500.pa04.json.join.ClientSetupInfoJson;
import cs3500.pa04.json.join.ClientSuccessfulHitsJson;
import cs3500.pa04.json.join.ServerEndGameJson;
import cs3500.pa04.json.join.ServerGiveVolleyShotsJson;
import cs3500.pa04.json.join.ServerSetupInfoJson;
import cs3500.pa04.json.join.ServerSuccessfulHitsJson;
import cs3500.pa04.json.wrappers.CoordJson;
import cs3500.pa04.json.wrappers.FleetSpecJson;
import cs3500.pa04.json.wrappers.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.BattleView;

/**
 * Proxy controller for communicating with server.
 */
public class ProxyController {

  private final Socket socket;
  private final Player player;
  private final InputStream in;
  private final PrintStream out;
  private final BattleView view;
  private final ObjectMapper mapper = new ObjectMapper();
  private ControllerInputHandler userInput;
  private List<Ship> shipsList;

  /**
   * Creates a ProxyController object given a player to use and a socket to connect to server.
   *
   * @param player Player to play on the server
   * @param socket Socket to connect to
   */
  public ProxyController(Player player, Socket socket) {
    this.player = player;
    this.socket = socket;
    try {
      this.in = socket.getInputStream();
      this.out = new PrintStream(socket.getOutputStream());
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid socket");
    }
    this.view = new BattleView(System.out);
    this.userInput = new ControllerInputHandler(new InputStreamReader(System.in));
  }

  /**
   * Play the BattleServo game
   */
  public void playGame() {
    this.run();
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.socket.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateJsonRequest(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
      throw new IllegalStateException("Invalid JSON or disconnected from server.");
    }
  }

  /**
   * Handles a JSON packet received from the server
   *
   * @param message Message received from server
   */
  public MessageJson delegateJsonRequest(MessageJson message) {
    return switch (message.methodName()) {
      case "join" -> this.handleJoinRequest(message);
      case "setup" -> this.handleSetupRequest(message);
      case "take-shots" -> this.handleTakeShotsRequest(message);
      case "report-damage" -> this.handleReportDamage(message);
      case "successful-hits" -> this.handleSuccessfulHits(message);
      case "end-game" -> this.handleEndGame(message);
      default -> throw new IllegalStateException("Unexpected value: " + message.methodName());
    };
  }

  /**
   * Handle a JSON request from the server to join
   */
  public MessageJson handleJoinRequest(MessageJson messageJson) {
    view.log("Enter your username:\n");
    String username = this.userInput.readLine();
    view.log("Enter 'SINGLE' for single-player or 'MULTI' for multiplayer:\n");
    String mode;
    do {
      mode = userInput.readLine();
    } while (!mode.equalsIgnoreCase("SINGLE")
        && !mode.equalsIgnoreCase("MULTI"));

    ClientJoinJson joinPacket = new ClientJoinJson(username, mode.toUpperCase());
    MessageJson returnMessage = new MessageJson("join", mapper.convertValue(joinPacket,
        JsonNode.class));

    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    System.out.println("success");
    return returnMessage;
  }

  /**
   * Handle a JSON request from the server to setup
   */
  public MessageJson handleSetupRequest(MessageJson messageJson) {
    System.out.println("success");
    ServerSetupInfoJson setupInfo = mapper.convertValue(messageJson.args(),
        ServerSetupInfoJson.class);
    FleetSpecJson converted = setupInfo.specifications();
    Map<ShipType, Integer> specifications = converted.getSpecsAsMap();
    this.shipsList = player.setup(setupInfo.height(), setupInfo.width(), specifications);
    ShipJson[] placement = new ShipJson[converted.calculateFleetSize()];
    for (int i = 0; i < shipsList.size(); i++) {
      Ship s = shipsList.get(i);
      CoordJson coordNode = new CoordJson(s.location.xpos, s.location.ypos);
      ShipJson current = new ShipJson(coordNode, Math.max(s.length, s.width), s.getRotation());
      placement[i] = current;
    }

    ClientSetupInfoJson setupPacket = new ClientSetupInfoJson(placement);
    MessageJson returnMessage = new MessageJson("setup",
        mapper.convertValue(setupPacket, JsonNode.class));


    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    return returnMessage;
  }

  /**
   * Handle a JSON request from the server to take shots
   */
  public MessageJson handleTakeShotsRequest(MessageJson messageJson) {
    List<Coord> shots = player.takeShots();
    CoordJson[] volleyList = new CoordJson[shots.size()];
    for (int i = 0; i < volleyList.length; i++) {
      Coord c = shots.get(i);
      CoordJson convertedCoord = new CoordJson(c.xpos, c.ypos);
      volleyList[i] = convertedCoord;
    }
    ClientGiveShotsJson takeshotsPacket = new ClientGiveShotsJson(volleyList);
    MessageJson returnMessage = new MessageJson("take-shots",
        mapper.convertValue(takeshotsPacket, JsonNode.class));
    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    return returnMessage;
  }

  /**
   * Handle a JSON request from the server to report damage
   */
  public MessageJson handleReportDamage(MessageJson messageJson) {
    System.out.println("success");
    ServerGiveVolleyShotsJson volleyInfo = mapper.convertValue(messageJson.args(),
        ServerGiveVolleyShotsJson.class);

    List<Coord> allVolleyShots = new ArrayList<>();

    for (CoordJson jcoord : volleyInfo.coords()) {
      allVolleyShots.add(jcoord.convertToCoord());
    }

    List<Coord> successfulHits = player.reportDamage(allVolleyShots);

    CoordJson[] playerDamage = new CoordJson[successfulHits.size()];
    for (int i = 0; i < successfulHits.size(); i++) {
      Coord c = successfulHits.get(i);
      playerDamage[i] = new CoordJson(c.xpos, c.ypos);
    }

    ClientReturnLandedHitsJson reportDamagePacket = new ClientReturnLandedHitsJson(playerDamage);
    MessageJson returnMessage = new MessageJson("report-damage",
        mapper.convertValue(reportDamagePacket, JsonNode.class));


    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    return returnMessage;
  }

  /**
   * Handle a JSON request from the server for successful hits
   */
  public MessageJson handleSuccessfulHits(MessageJson messageJson) {
    ServerSuccessfulHitsJson successfulHits = mapper.convertValue(messageJson.args(),
        ServerSuccessfulHitsJson.class);

    List<Coord> allSuccessfulShots = new ArrayList<>();

    for (CoordJson coord : successfulHits.successfulHits()) {
      allSuccessfulShots.add(coord.convertToCoord());
    }

    this.player.successfulHits(allSuccessfulShots);

    ClientSuccessfulHitsJson successfulHitsPacket = new ClientSuccessfulHitsJson();
    MessageJson returnMessage = new MessageJson("successful-hits",
        mapper.convertValue(successfulHitsPacket, JsonNode.class));

    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    return returnMessage;
  }

  /**
   * Handle a JSON request from the server to end game
   */
  public MessageJson handleEndGame(MessageJson messageJson) {
    try {
      this.socket.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not close socket.");
    }
    ServerEndGameJson gameEnd = mapper.convertValue(messageJson.args(),
        ServerEndGameJson.class);
    GameResult ending;
    switch (gameEnd.gameResult()) {
      case "WIN":
        ending = GameResult.WIN;
        break;
      case "LOSE":
        ending = GameResult.LOSE;
        break;
      default:
        ending = GameResult.TIE;
        break;
    }
    this.player.endGame(ending, gameEnd.endReason());
    ClientEndGameJson endGamePacket = new ClientEndGameJson();
    MessageJson returnMessage = new MessageJson("end-game",
        mapper.convertValue(endGamePacket, JsonNode.class));

    this.out.println(mapper.convertValue(returnMessage, JsonNode.class));
    return returnMessage;
  }

  public ObjectMapper getMapper() {
    return this.mapper;
  }

  public void setUserInput(ControllerInputHandler input) {
    this.userInput = input;
  }
}
