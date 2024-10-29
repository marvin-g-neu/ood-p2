package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.PlayerColor;

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
   */
    boolean isLegalMove(Cell cell, CustomCard card);

  /**
   * Executes the battle phase after a card is placed.
   *
   * @param placedCard the newly placed card
   * @param row the row where the card was placed
   * @param col the column where the card was placed
   * @param currentPlayer the player who placed the card
   */
  void executeBattlePhase(CustomCard placedCard, int row, int col, PlayerColor currentPlayer);

  /**
   * Gets the opposite direction of a given direction.
   *
   * @param direction the direction
   * @return the opposite direction
   */
  String getOppositeDirection(String direction);

  /**
   * Determines if the game has ended based on the current state.
   *
   * @return true if the game should end, false otherwise
   */
  boolean isGameOver();
}
