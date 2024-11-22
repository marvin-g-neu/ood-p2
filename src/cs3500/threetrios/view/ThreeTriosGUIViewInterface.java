package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;

/**
 * A view interface specific to a GUI view of Three Trios. For anything that
 * shouldn't be modifying the view, the base ThreeTriosView should be used.
 */
public interface ThreeTriosGUIViewInterface extends ThreeTriosView {
  /**
   * Handle the effect of the selection of a cell at the given coordinates.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @throws IllegalArgumentException if row or col is out of range for the board
   */
  void handleCellClick(int row, int col);
}
