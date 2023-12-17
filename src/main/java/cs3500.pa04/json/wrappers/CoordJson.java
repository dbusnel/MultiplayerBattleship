package cs3500.pa04.json.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.Coord;

/**
 * Represents a .json Coordinate object
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
  /**
   * Return this record as a Coord object
   *
   * @return Coord
   */
  public Coord convertToCoord() {
    return new Coord(this.x, this.y);
  }
}
