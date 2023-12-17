package json.wrappers;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.ShipType;
import cs3500.pa04.json.wrappers.FleetSpecJson;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test FleetSpecJson wrapper class
 */
class FleetSpecJsonTest {
  HashMap<ShipType, Integer> specifications;

  /**
   * Test converting FleetSpecJson to a hashmap
   */
  @Test
  void getSpecsAsMap() {
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    FleetSpecJson test = new FleetSpecJson(1,
        1, 1, 1);
    Map<ShipType, Integer> actualMap = test.getSpecsAsMap();
    assertEquals(specifications.keySet().size(), actualMap.keySet().size());
    for (ShipType s : actualMap.keySet()) {
      assertEquals(actualMap.get(s), specifications.get(s));
    }
  }
}