package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;

import java.util.List;

/**
 * A Three Trios Model interface only containing the accessor methods and no mutator methods.
 * Intended to be used by the view in order to better encapsulate functionality.
 */
public interface ReadOnlyThreeTriosModelInterface {
  /**
   * Gets the state of the cell at a given position.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @return the cell at the given position
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalStateException    if the game has not been started
   */
  CellState getCellStateAt(int row, int col);

  /**
   * Gets the current player.
   *
   * @return the current player
   * @throws IllegalStateException if the game has not been started or is over
   */
  PlayerColor getCurrentPlayer();

  /**
   * Gets a copy of the current player's hand.
   *
   * @return the current player's hand
   * @throws IllegalStateException if the game has not been started or is over
   */
  List<CustomCard> getCurrentPlayerHand();

  /**
   * Gets a copy of the hand of a given player.
   *
   * @param player the player to get the hand of
   * @return the hand of the player
   * @throws IllegalStateException    if the game has not been started
   * @throws IllegalArgumentException if player is null
   */
  List<CustomCard> getPlayerHand(PlayerColor player);

  /**
   * Gets a copy of the grid currently in play.
   *
   * @return the grid in play
   * @throws IllegalStateException if the game has not been started
   */
  Grid getGrid();

  /**
   * Gets the score of the given player.
   *
   * @param player the checked player
   * @return the score of the given player
   * @throws IllegalArgumentException if player is null
   * @throws IllegalStateException    if the game has not been started
   */
  int getScore(PlayerColor player);

  /**
   * Gets the current state of the game.
   *
   * @return the game state
   */
  GameState getGameState();
}
