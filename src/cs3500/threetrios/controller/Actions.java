package cs3500.threetrios.controller;

/**
 * The features interface represents the functionality of the controller
 * in the MVC architecture for Three Trios.
 */
public interface Actions {

  /**
   * Defines the action for when a cell on the grid is clicked by a player.
   *
   * @param rowIdx is the row index of a cell
   * @param colIdx is the column index of a cell
   */
  void selectCell(int rowIdx, int colIdx);

  /**
   * Defines the action for when a card in the player's hand is clicked.
   *
   * @param playerName is the name of the player whose hand was clicked
   * @param cardIdx is the index of the selected card from the player's hand
   * @return true if card selected successfully, false if unsuccessful
   */
  boolean selectCard(String playerName, int cardIdx);
}