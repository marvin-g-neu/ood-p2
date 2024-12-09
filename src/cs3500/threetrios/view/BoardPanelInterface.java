package cs3500.threetrios.view;

import cs3500.threetrios.controller.GameListeners;

/**
 * An interface for managing the Panel representing the current board state.
 */
public interface BoardPanelInterface {
  /**
   * Handle the effect of the selection of a cell at the given coordinates.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @throws IllegalArgumentException if row or col is out of range for the board
   */
  void handleCellClick(int row, int col);

  /**
   * Set the linked controller to this view.
   *
   * @param controller the linked controller
   */
  void setController(GameListeners controller);
}
