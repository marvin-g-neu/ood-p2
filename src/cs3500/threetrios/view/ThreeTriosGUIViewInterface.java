package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;

import javax.swing.JButton;

/**
 * A view interface specific to a GUI view of Three Trios. For anything that
 * shouldn't be modifying the view, the base ThreeTriosView should be used.
 */
public interface ThreeTriosGUIViewInterface extends ThreeTriosView {
  /**
   * Handle the effect of a card in a player's hand being clicked.
   *
   * @param player    the player whose hand has the clicked card
   * @param handIndex the index of the card in the player's hand
   * @throws IllegalArgumentException if player is null
   * @throws IllegalArgumentException if handIndex is out of range for the player's hand
   */
  void handleCardClick(PlayerColor player, int handIndex);

  /**
   * Handle the effect of the selection of a cell at the given coordinates.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @throws IllegalArgumentException if row or col is out of range for the board
   */
  void handleCellClick(int row, int col);
}
