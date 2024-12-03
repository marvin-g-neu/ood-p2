package cs3500.threetrios.provider.model.cells;

import java.util.HashMap;
import java.util.Map;

/**
 * Directions for the cells.
 * All these directions will be associated with an opposite direction for the battle phase.
 */
public enum Directions {
  NORTH(0, -1),
  SOUTH(0, 1),
  EAST(1, 0),
  WEST(-1, 0);


  public final int dx;
  public final int dy;

  /**
   * Constructor for the Directions Enum to set associated changes in the coordinate plane to a
   * cellinal direction.
   *
   * @param dx associated change in x.
   * @param dy associated change in y.
   */
  private Directions(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  /**
   * The map where opposite directions are mapped to each other.
   */
  private static final Map<Directions, Directions> opposite;


  // Initializing the opposite map.
  static {
    opposite = new HashMap<>();
    opposite.put(NORTH, SOUTH);
    opposite.put(SOUTH, NORTH);
    opposite.put(EAST, WEST);
    opposite.put(WEST, EAST);
  }

  /**
   * return the opposite direction from the given direction.
   *
   * @param dir the direction.
   * @return the opposite direction.
   * @throws IllegalArgumentException if dir is null.
   */
  public static Directions getOpposite(Directions dir) {
    if (dir == null) {
      throw new IllegalArgumentException("dir is null");
    }
    return opposite.get(dir);
  }
}
