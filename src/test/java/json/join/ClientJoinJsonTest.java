package json.join;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.GameType;
import cs3500.pa04.json.join.ClientJoinJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test methods for client join packet
 */
class ClientJoinJsonTest {
  ClientJoinJson test;
  ClientJoinJson test2;

  /**
   * Set up examples for testing
   */
  @BeforeEach
  void setUp() {
    test = new ClientJoinJson("David", "SINGLE");
    test2 = new ClientJoinJson("Computer", "MULTI");
  }

  /**
   * Test getting username
   */
  @Test
  void getUserName() {
    assertEquals(test.username(), "David");
    assertEquals(test2.username(), "Computer");
  }

  /**
   * Test getting GameType as an enumerated type
   */
  @Test
  void getGameType() {
    assertEquals(test.getGameType(), GameType.SINGLE);
    assertEquals(test2.getGameType(), GameType.MULTI);
  }
}