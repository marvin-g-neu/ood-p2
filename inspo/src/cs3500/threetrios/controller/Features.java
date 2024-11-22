package cs3500.threetrios.controller;

/**
 * Interface representing the features (controller) in the MVC architecture.
 */
public interface Features {

  /**
   * Handles the action when a card in the player's hand is selected.
   *
   * @param player  the player whose hand was clicked
   * @param cardIdx the index of the selected card
   * @return true if card selected successfully, false if unsuccessful
   */
  boolean selectCard(String player, int cardIdx);

  /**
   * Handles the action when a grid cell is clicked.
   *
   * @param row the row index of the cell
   * @param col the column of the cell
   */
  void clickCell(int row, int col);
}
