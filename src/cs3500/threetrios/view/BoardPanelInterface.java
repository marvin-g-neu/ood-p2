package cs3500.threetrios.view;

import javax.swing.JPanel;

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
   * Get the JPanel for this board.
   *
   * @return the JPanel representing the board
   */
  JPanel getPanel();
}
