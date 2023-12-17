package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import controller.AutoPlayer;
import controller.ControllerInputHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.join.ClientJoinJson;
import cs3500.pa04.json.join.ServerEndGameJson;
import cs3500.pa04.json.join.ServerGiveVolleyShotsJson;
import cs3500.pa04.json.join.ServerJoinRequestJson;
import cs3500.pa04.json.join.ServerRequestShotsJson;
import cs3500.pa04.json.join.ServerSetupInfoJson;
import cs3500.pa04.json.wrappers.CoordJson;
import cs3500.pa04.json.wrappers.FleetSpecJson;
import java.io.InputStream;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.BattleView;

/**
 * Test class for ProxyController
 */
class ProxyControllerTest {
  AutoPlayer testPlayer;
  MocketBad mocketBad;
  MocketGood mocketGood;
  InputStream in;
  MockOutputStream out;
  ProxyController controller;
  ObjectMapper mapper;
  StringBuilder testOutput;

  /**
   * Set up examples for testing.
   */
  @BeforeEach
  void setUp() {
    testOutput = new StringBuilder();
    testPlayer = new AutoPlayer();
    in = System.in;
    out = new MockOutputStream(System.out);
    mocketBad = new MocketBad(in, out);
    mocketGood = new MocketGood(in, out);
    controller = new ProxyController(testPlayer, mocketGood);
    mapper = controller.getMapper();
  }

  /**
   * Test setting up game
   */
  @Test
  void playGame() {
    //mocketbad represents a bad socket
    assertThrows(IllegalArgumentException.class,
        () -> controller = new ProxyController(testPlayer, mocketBad));
  }

  /**
   * Test handling of join JSON packet
   */
  @Test
  void handleJoinRequest() {
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("join",
            mapper.convertValue(new ServerJoinRequestJson(), JsonNode.class));
    StringReader testInput = new StringReader("David\nSINGLE");
    controller.setUserInput(new ControllerInputHandler(testInput));
    ClientJoinJson expected = new ClientJoinJson("\"David\"", "\"SINGLE\"");
    MessageJson actual = controller.delegateJsonRequest(request);

    assertEquals("join", actual.methodName());
    assertEquals(expected.username(), actual.args().get("name").toString());
    assertEquals(expected.gameType(), actual.args().get("game-type").toString());
  }

  /**
   * Test handling of setup JSON packet
   */
  @Test
  void handleSetupRequest() {
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("setup",
            mapper.convertValue(new ServerSetupInfoJson(6, 6,
            new FleetSpecJson(1, 1, 1, 1)),
    JsonNode.class));

    MessageJson actual = controller.delegateJsonRequest(request);

    assertEquals("setup", actual.methodName());
    //we aren't necessarily concerned about the fleet itself, just that setup() was called properly
    assertNotNull(actual.args().get("fleet"));
  }

  /**
   * Test handling of take-shots JSON packet
   */
  @Test
  void handleTakeShotsRequest() {
    this.handleSetupRequest();
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("take-shots",
            mapper.convertValue(new ServerRequestShotsJson(), JsonNode.class));

    MessageJson actual = controller.delegateJsonRequest(request);

    assertEquals("take-shots", actual.methodName());
    //we aren't necessarily concerned about the volley itself, just that takeShots was called
    assertNotNull(actual.args().get("coordinates"));
  }

  /**
   * Test handling of report-damage packet
   */
  @Test
  void handleReportDamage() {
    this.handleSetupRequest();
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("report-damage",
            mapper.convertValue(new ServerGiveVolleyShotsJson(new CoordJson[] {
                new CoordJson(1, 1)}), JsonNode.class));

    MessageJson actual = controller.delegateJsonRequest(request);

    assertEquals("report-damage", actual.methodName());
    //we aren't necessarily concerned about the shots, just that reportDamage was called
    // (thus an array is in the JSON)
    assertNotNull(actual.args().get("coordinates"));
  }

  /**
   * Test handling of successful-hits JSON packet
   */
  @Test
  void handleSuccessfulHits() {
    testPlayer.setView(new BattleView(this.testOutput));
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("successful-hits",
            mapper.convertValue(new ServerGiveVolleyShotsJson(new CoordJson[]
                {new CoordJson(0, 1), new CoordJson(3, 2)}), JsonNode.class));

    MessageJson actual = controller.delegateJsonRequest(request);
    assertEquals("successful-hits", actual.methodName());
    assertEquals("Successful hits:\n(0, 1)\n(3, 2)\n", testPlayer.view.output.toString());
  }

  /**
   * Test handling of end-game packet
   */
  @Test
  void handleEndGame() {
    testPlayer.setView(new BattleView(this.testOutput));
    //represent the packet sent by the server
    MessageJson request =
        new MessageJson("end-game",
            mapper.convertValue(new ServerEndGameJson("WIN",
                "Player 1 sank all of Player 2's ships"), JsonNode.class));


    MessageJson actual = controller.delegateJsonRequest(request);
    assertEquals("end-game", actual.methodName());
    assertEquals("WIN: Player 1 sank all of Player 2's ships",
        testPlayer.view.output.toString());
  }
}