package cs3500.threetrios.model;

import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.CellColor;
import cs3500.threetrios.model.grid.Grid;

import java.util.List;

/**
 * Model interface for a game of Three Trios.
 */
public interface TTModel {
  // Basic interface for the Three Trios game model
  // for this homework, we'll only implement the classical version
  // but we'll leave this interface so that we can easily add
  // new versions of the game in the future into the abstract class

  /**
   * Starts a game with the given grid and deck.
   *
   * @param gameGrid the grid for the game
   * @param deck     the deck for the game
   * @throws IllegalArgumentException if the Grid is null or the deck contains null values
   * @throws IllegalArgumentException if the grid has an even number of card cells
   * @throws IllegalArgumentException if the deck does not have more cards than card cells
   * @throws IllegalStateException    if there is a game in play
   */
  void startGame(Grid gameGrid, List<CustomCard> deck);

  /**
   * Gets the cell at a given position.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @return the cell at the given position
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalStateException    if the game has not been started
   */
  CellColor getCellAt(int row, int col);

  /**
   * Plays the given card to the cell at the given coordinates,
   * then initiates battles if possible. Ends turn.
   *
   * @param row       the row of the cell
   * @param col       the column of the cell
   * @param handIndex the index in the current player's hand being played from
   * @throws IllegalArgumentException if the index is not in range
   * @throws IllegalArgumentException if the row or column is not in range
   * @throws IllegalStateException    if the cell already has a card
   * @throws IllegalStateException    if the cell is not a card cell
   * @throws IllegalStateException    if the game has not been started or is over
   */
  void playTurn(int row, int col, int handIndex);

  /**
   * If the game should end, end the game. Otherwise, pass the turn.
   *
   * @throws IllegalStateException if the game has not been started or is over
   */
  void endTurn();

  /**
   * Gets the current player.
   *
   * @return the current player
   * @throws IllegalStateException if the game is over
   * @throws IllegalStateException if the game has not been started
   */
  String getCurrentPlayer();

  /**
   * Gets the current player's hand.
   *
   * @return the current player's hand
   * @throws IllegalStateException if the game has not been started
   */
  List<String> getCurrentPlayerHand();

  /**
   * Gets the grid currently in play.
   *
   * @return the grid in play
   * @throws IllegalStateException if the game has not been started
   */
  Grid getGrid();

  /**
   * Determines whether the attacker has a higher attack value.
   *
   * @param attacker the value used by the attacker
   * @param defender the value used by the defender
   * @return true if the attacker's value is higher, false otherwise
   * @throws IllegalArgumentException if either value is null
   */
  boolean attackerWinsBattle(AttackValue attacker, AttackValue defender);
}
