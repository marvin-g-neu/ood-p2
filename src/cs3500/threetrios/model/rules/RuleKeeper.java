package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.grid.Grid;

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
   * Determines whether the attacker wins a battle.
   *
   * @param attacker        the attacking cell
   * @param defender        the defending cell
   * @param attackDirection the direction the attack is in
   * @return returns true if the attacker wins, false otherwise
   * @throws IllegalArgumentException if any value is null
   */
  boolean attackerWinsBattle(CustomCard attacker, CustomCard defender, Direction attackDirection);

  /**
   * Executes the battle phase after a card is placed.
   *
   * @param row           the row where the card was placed
   * @param col           the column where the card was placed
   * @param currentPlayer the player who placed the card
   * @throws IllegalArgumentException if currentPlayer is null
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalArgumentException if the cell does not have a card
   * @throws IllegalStateException    if model state is not IN_PROGRESS
   */
  void executeBattlePhase(int row, int col, PlayerColor currentPlayer);

  /**
   * Executes the battle phase after a card is placed.
   *
   * @param row           the row where the card was placed
   * @param col           the column where the card was placed
   * @param currentPlayer the player who placed the card
   * @param givenGrid          the grid on which to execute the battle
   * @throws IllegalArgumentException if currentPlayer is null
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalArgumentException if the cell does not have a card
   * @throws IllegalStateException    if model state is not IN_PROGRESS
   */
  void executeBattlePhase(int row, int col, PlayerColor currentPlayer, Grid givenGrid);

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

  /**
   * Simulates playing a card at given coordinates and returns number of cards
   * that would be flipped.
   *
   * @param row           row coordinate
   * @param col           column coordinate
   * @param cardInHandIdx index of card in hand to simulate playing
   * @param currentPlayer the player who would play the card
   * @return number of cards that would be flipped
   * @throws IllegalArgumentException if coordinates invalid or handIndex invalid
   */
  int getPotentialFlips(int row, int col, int cardInHandIdx, PlayerColor currentPlayer);
}
