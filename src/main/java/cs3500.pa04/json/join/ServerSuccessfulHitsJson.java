package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.CoordJson;

/**
 * Reports which of the shots sent to the client are successful
 *
   * @param successfulHits The hits that successfully landed
 */
public record ServerSuccessfulHitsJson(
    @JsonProperty("coordinates") CoordJson[] successfulHits) {
}
