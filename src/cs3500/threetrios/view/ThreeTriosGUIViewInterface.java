package cs3500.threetrios.view;

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

  /**
   * Set the frame to be visible or not visible.
   *
   * @param visible whether the frame should be visible or not
   */
  void setVisible(boolean visible);

  /**
   * Display a message on the GUI view.
   *
   * @param message the message to display
   */
  void displayMessage(String message);
}
