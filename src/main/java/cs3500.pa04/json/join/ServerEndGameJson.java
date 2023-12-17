package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record sent by the server representing the end of the game
 *
 * @param gameResult The result of the game
 * @param endReason The reason for the game to end
 */
public record ServerEndGameJson(
    @JsonProperty("result") String gameResult,
    @JsonProperty("reason") String endReason) { }
