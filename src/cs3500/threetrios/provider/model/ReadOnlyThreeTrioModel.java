package cs3500.threetrios.provider.model;

import java.util.List;
import java.util.Map;

import cs3500.threetrios.provider.model.cells.Cell;

/**
 * An interface that is read-only for a model. Meant for the view to ensure that the view cannot
 * change the model.
 */
public interface ReadOnlyThreeTrioModel {
  /**
   * Gets the hand of the inputted color.
   *
   * @param color the color of the player
   * @return the list of the inputted player's hand
   */
  List<Cell> getHand(ThreeTrioColor color);

  /**
   * Returns a copy of the board for the relevant ThreeTrioGame.
   *
   * @return the board for the game.
   * @throws IllegalStateException if game has not been started.
   */
  Cell[][] getBoard();

  /**
   * Gets all the current colors in the current game.
   *
   * @return all the current colors in the current game.
   */
  List<ThreeTrioColor> getAllPlayers();

  /**
   * Returns if the game is over.
   *
   * @return true if the game is over, and false otherwise.
   */
  boolean isGameOver();

  /**
   * Returns if the game has started.
   *
   * @return true if the game has started and false otherwise.
   */
  boolean hasGameStarted();

  /**
   * Returns a List of the winner of the game.
   *
   * @return the ThreeTrioColor of the winner, null if tied game.
   * @throws IllegalStateException if the game is not over.
   */
  List<ThreeTrioColor> getWinner();


  /**
   * Gets a map of each color's total amount of captured spaces,map is sorted.
   *
   * @return A map of ThreeTrioColors to the amount of spaces they occupy on the board.
   */
  Map<ThreeTrioColor, Integer> getScores();

}
