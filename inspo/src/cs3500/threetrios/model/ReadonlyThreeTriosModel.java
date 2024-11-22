package cs3500.threetrios.model;

import java.util.List;

/**
 * Behaviors for a read-only game of Three Trios, consisting solely of game state observations.
 * The game consists of a deck of cards, a hand for each player to play from,
 * and a grid of cells to play to.
 * The goal of the game is to own more cards than the opponent upon filling all empty card cells.
 * The winner is determined by counting the number of cards each player owns both on the grid
 * and in their hands. The player with the most owned cards wins.
 * If no such player exists, the game is a tie.
 * The implementation determines how the origin (0,0) is defined for the model.
 *
 * @param <C> the type of cards used
 */
public interface ReadonlyThreeTriosModel<C extends Card> {

  /**
   * Represents the status of the game.
   */
  enum GameStatus {
    NotStarted, Playing, GameOver
  }

  /**
   * Returns the number of cards remaining in the deck used in the game.
   *
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  int numOfCardsInDeck();

  /**
   * Returns the width of the grid in the game.
   *
   * @return the width of the game grid
   * @throws IllegalStateException if the game has not started
   */
  int getGridWidth();

  /**
   * Returns the height of the grid in the game.
   *
   * @return the height of the game grid
   * @throws IllegalStateException if the game has not started
   */
  int getGridHeight();

  /**
   * Returns if the game is over as specified by the implementation.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Returns if the game is won by a player as specified by the implementation.
   *
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game is not over
   */
  boolean isGameWon();

  /**
   * Returns the player that won the game as specified by the implementation.
   *
   * @return the player that won the game
   * @throws IllegalStateException if the game has not over or the game is not won
   */
  PlayerColor getWinner();

  /**
   * Returns a copy of the current player's hand in the game. This means modifying
   * the returned list or the cards in the list has no effect on the game.
   *
   * @return a new list containing the cards in the player's hand in the same order
   *         as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   */
  List<C> getHand(PlayerColor player);

  /**
   * Returns a copy of the card at the given coordinates of the grid.
   * Modifying this card has no effect on the game.
   *
   * @param row a 0-index number representing the row of the cell to be queried
   * @param col a 0-index number representing the column of the cell to be queried
   * @return the card at the given cell in the grid
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the given cell is out of bounds or is not a card cell
   */
  C getCardAt(int row, int col);

  /**
   * Returns the cell type of the cell at the given coordinates of the grid.
   *
   * @param row a 0-index number representing the row of the cell to be queried
   * @param col a 0-index number representing the column of the cell to be queried
   * @return the cell type of the given cell in the grid
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the given cell is out of bounds
   */
  CellType getCellTypeAt(int row, int col);

  /**
   * Returns the player that currently owns the card at the given cell.
   * If there is no card (and therefore no player) at the current cell, returns null.
   *
   * @param row a 0-index number representing the row of the cell to be queried
   * @param col a 0-index number representing the column of the cell to be queried
   * @return the player that owns the card at the given cell (if it exists)
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the given cell is out of bounds or is not a card cell
   */
  PlayerColor getCellPlayerAt(int row, int col);

  /**
   * Returns the current status of the game. A game can be not started, in progress, or complete.
   *
   * @return the status of the game
   */
  GameStatus getStatus();

  /**
   * Returns the current player of the game. It is this player's turn to play a card to the grid.
   *
   * @return the current player
   * @throws IllegalStateException if the game has not started or the game is over
   */
  PlayerColor getCurrentPlayer();

  /**
   * Returns a copy of the game's grid. Mutating the copied grid has no effect on the game state.
   *
   * @return a copy of the game grid
   * @throws IllegalStateException if the game has not started
   */
  Cell[][] gridCopy();

  /**
   * Determines whether it is legal for a player to play to the cell at the given coordinates.
   * A player can play to cell as long as the cell is a card cell and is not occupied by
   * another card.
   *
   * @param row a 0-index number representing the row of the cell to be queried
   * @param col a 0-index number representing the column of the cell to be queried
   * @return true if it is legal, false if not
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if the given cell is out of bounds
   */
  boolean legalPlayToCell(int row, int col);

  /**
   * Returns the number of cards that would be flipped by the current player if the given card was
   * played to the cell at the given coordinates.
   *
   * @param row           a 0-index number representing the row of the cell to be queried
   * @param col           a 0-index number representing the column of the cell to be queried
   * @param cardInHandIdx a 0-index number representing the card to play from the hand
   * @return the number of cards that would be flipped
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if the given cell is out of bounds or cannot be played to
   */
  int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx);

  /**
   * Returns the current game score for the given player.
   * A player's score is the number of cards the player owns on the grid plus the number of cards
   * the player owns in their hand.
   *
   * @param playerColor the given player
   * @return the player's game score
   * @throws IllegalStateException if the game has not started
   */
  int getPlayerScore(PlayerColor playerColor);
}
