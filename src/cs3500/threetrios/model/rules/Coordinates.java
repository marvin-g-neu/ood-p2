package cs3500.threetrios.model.rules;

/**
 * Represents a coordinate on the grid.
 */
public class Coordinates {
  private final int row;
  private final int col;

  /**
   * Constructs a Coordinates with the given row and column.
   *
   * @param row the row
   * @param col the column
   */
  public Coordinates(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row of the coordinate.
   *
   * @return the row
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Gets the column of the coordinate.
   *
   * @return the column
   */
  public int getColumn() {
    return this.col;
  }
}
