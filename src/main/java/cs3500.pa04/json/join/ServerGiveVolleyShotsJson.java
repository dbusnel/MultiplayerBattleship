package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.CoordJson;

/**
 * Represents the list of shots provided from the server
 *
 * @param coords the array of CoordJson objects
 */
public record ServerGiveVolleyShotsJson(
    @JsonProperty("coordinates") CoordJson[] coords) { }
