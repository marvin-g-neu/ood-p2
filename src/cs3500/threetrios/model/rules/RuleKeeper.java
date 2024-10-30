package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.CustomCard;

/**
 * Interface for managing game rules in Three Trios.
 * Allows for different rule variations in future implementations.
 */
public interface RuleKeeper {
  /**
   * Checks if a move is legal according to game rules.
   *
   * @param cell the cell where the card would be placed
   * @param card the card being placed
   * @return true if the move is legal, false otherwise
   * @throws IllegalArgumentException if either argument is null
   * @throws IllegalStateException    if model state is not IN_PROGRESS
   */
  boolean isLegalMove(Cell cell, CustomCard card);

  /**
   * Executes the battle phase after a card is placed.
   *
   * @param row           the row where the card was placed
   * @param col           the column where the card was placed
   * @param currentPlayer the player who placed the card
   * @throws IllegalArgumentException if any argument is null
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalArgumentException if the cell does not have a card
   * @throws IllegalStateException    if model state is not IN_PROGRESS
   */
  void executeBattlePhase(int row, int col, PlayerColor currentPlayer);

  /**
   * Gets the opposite direction of a given direction.
   *
   * @param direction the direction
   * @return the opposite direction
   * @throws IllegalArgumentException if direction is null
   */
  Direction getOppositeDirection(Direction direction);

  /**
   * Determines if the game was played to conclusion based on the current board state.
   *
   * @return true if the game has played to conclusion, false otherwise
   * @throws IllegalStateException if game has not started
   */
  boolean isGameCompleted();
}
