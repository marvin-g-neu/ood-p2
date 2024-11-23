package cs3500.threetrios.controller;

/**
 * Represents controller functionality in MVC.
 */
public interface Actions {

  /**
   * Action when a grid cell is clicked.
   *
   * @param rowIdx row index of the cell
   * @param colIdx column index of the cell
   */
  void selectCell(int rowIdx, int colIdx);

  /**
   * Action when a card is clicked.
   *
   * @param playerName name of the player
   * @param cardIdx index of the selected card
   * @return true if selection is successful
   */
  boolean selectCard(String playerName, int cardIdx);
}