package cs3500.threetrios.provider.model.cells;

import cs3500.threetrios.provider.model.ThreeTrioColor;

/**
 * Interface used to represent a Cell object for use in ThreeTrio Games.
 * With each cell having values for each direction and a color.
 */
public interface Cell {
  /**
   * Get the value of the cell at the given direction.
   *
   * @param dir enum of Directions type, this clarifies the position requested.
   * @return The int value at the requested direction of the Cell.
   * @throws IllegalArgumentException if dir is null.
   */
  int getValue(Directions dir);

  /**
   * Takes in a direction and returns the display name of the associated value.
   *
   * @param dir enum of Directions type, this clarifies the position requested.
   * @return the display name of the number at the direction.
   * @throws IllegalArgumentException if dir is null.
   */
  String toDisplayName(Directions dir);

  /**
   * Method used to change the color of a Cell to the corresponding player.
   *
   * @param color enum of ThreeTrioColor type to change the cell to.
   * @throws IllegalArgumentException if color is null.
   */
  void changeColor(ThreeTrioColor color);

  /**
   * Returns the color of the cell.
   *
   * @return Color enum of the cell.
   */
  ThreeTrioColor getColor();

  /**
   * Copies a cell so the original is not mutated by the receiver.
   *
   * @return the copied cell.
   */
  Cell copy();

  /**
   * Checks if the cell is a hole.
   *
   * @return true if the Cell is a hole, false otherwise.
   */
  boolean isaHole();


  /**
   * Returns a string representation of the Cell in the format:
   * "&lt;name&gt; &lt;northValue&gt; &lt;southValue&gt; &lt;eastValue&gt; &lt;westValue&gt;".
   *
   * @return the String of the Cell.
   */
  @Override
  String toString();
}
