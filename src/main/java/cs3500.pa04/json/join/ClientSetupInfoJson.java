package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.ShipJson;

/**
 * Contains the information sent by the client to setup game
 */
public record ClientSetupInfoJson(
    @JsonProperty("fleet") ShipJson[] fleet) { }
