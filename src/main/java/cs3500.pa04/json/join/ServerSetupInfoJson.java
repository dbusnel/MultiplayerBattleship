package cs3500.pa04.json.join;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.wrappers.FleetSpecJson;

/**
 * Contains the information sent by the server to setup game
 */
public record ServerSetupInfoJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpecJson specifications) { }
