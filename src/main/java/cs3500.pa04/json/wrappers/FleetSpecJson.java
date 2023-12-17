package cs3500.pa04.json.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a .json FleetSpec object
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int numCarriers,
    @JsonProperty("BATTLESHIP") int numBattleships,
    @JsonProperty("DESTROYER") int numDestroyers,
    @JsonProperty("SUBMARINE") int numSubmarines) {

  /**
   * Return the FleetSpecJson as a HashMap
   *
   * @return Map of ShipType, Integer
   */
  public Map<ShipType, Integer> getSpecsAsMap() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, numCarriers);
    specifications.put(ShipType.BATTLESHIP, numBattleships);
    specifications.put(ShipType.DESTROYER, numDestroyers);
    specifications.put(ShipType.SUBMARINE, numSubmarines);
    return specifications;
  }


  public int calculateFleetSize() {
    return this.numBattleships + this.numCarriers + this.numDestroyers + this.numSubmarines;
  }
}
