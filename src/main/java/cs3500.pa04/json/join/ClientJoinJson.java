package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.GameType;

/**
 * Represents the packet returned by the client to the server to join
 */
public record ClientJoinJson(
    @JsonProperty("name") String username,
    @JsonProperty("game-type") String gameType) {
  /**
   * Get the game type
   */
  public GameType getGameType() {
    if (this.gameType.equals("SINGLE")) {
      return GameType.SINGLE;
    } else {
      return GameType.MULTI;
    }
  }
}
