package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.CoordJson;

/**
 * The packet given by the client containing a list of the volley shots
 */
public record ClientGiveShotsJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {
}
