package cs3500.threetrios.provider.model;

import java.util.List;
import java.util.Map;

import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.players.Player;
import cs3500.threetrios.provider.model.players.ai.StrategyEnum;

/**
 * A ThreeTrioModel that allows the user to start a game,
 * play a cell to the board, and checks if the game is over.
 * This interface always mutation of the game state.
 */
public interface ThreeTrioModel extends ReadOnlyThreeTrioModel {
  /**
   * Starts the game and sets up what is need for the game to start.
   * This method takes in a deck and a board and distributes the cell to the created players.
   * This method can be used to restart the game if the game is over.
   *
   * @param deck        the List of Cell objects to be used for the game.
   * @param board       the starting state of the board.
   * @param shuffle     if the deck should be shuffled before playing.
   * @param strategyMap a Map of players and the strategy relevant to them,
   *                    where null represents human.
   * @throws IllegalStateException    if game is in the playing state.
   * @throws IllegalArgumentException if deck is null.
   * @throws IllegalArgumentException if board is null.
   * @throws IllegalArgumentException if strategyMap is null.
   * @throws IllegalArgumentException if the rows of the array are different sizes.
   * @throws IllegalArgumentException if one of the arrays in board is null.
   * @throws IllegalArgumentException if the deck has a null cell.
   * @throws IllegalArgumentException if the deck has a hole.
   * @throws IllegalArgumentException if the deck does not have enough cell to deal.
   */
  void startGame(List<Cell> deck, Cell[][] board,
                 Map<ThreeTrioColor, List<StrategyEnum>> strategyMap, boolean shuffle);

  /**
   * Places the given cell on the board at the requested coordinates.
   *
   * @param cell the cell object to be played.
   * @param x    the x position on the board (0 based).
   * @param y    the 7 position on the board (0 based).
   * @throws IllegalStateException    if game is not playing.
   * @throws IllegalArgumentException if x is out of bounds.
   * @throws IllegalArgumentException if y is out of bounds.
   * @throws IllegalArgumentException if cell is null.
   * @throws IllegalArgumentException if cell is a hole.
   * @throws IllegalArgumentException if there is already is a cell at the position x, y.
   * @throws IllegalStateException    if the game is not in the playing phase.
   */
  void playCell(Cell cell, int x, int y);

  /**
   * Gets the Player Object related to the given color.
   *
   * @param color ThreeTrioColor requested to get a Player.
   * @return the player associated with the color.
   * @throws IllegalStateException    if game has not been started.
   * @throws IllegalArgumentException if the color is null.
   */
  Player getPlayer(ThreeTrioColor color);

}
