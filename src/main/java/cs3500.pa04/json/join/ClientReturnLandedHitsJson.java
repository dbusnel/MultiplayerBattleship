package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.CoordJson;

/**
 * Return the correct landed hits from the client
 *
 * @param coords The array of successful hits
 */
public record ClientReturnLandedHitsJson(
    @JsonProperty("coordinates") CoordJson[] coords
) {
}
