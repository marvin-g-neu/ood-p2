package cs3500.threetrios.provider.model.players;

import java.util.List;

import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.ThreeTrioColor;

/**
 * Interface relating to Players of the ThreeTrios Game
 * where the players can play a cell to the model and have a specific hand and a specific color.
 */
public interface Player {
  /**
   * Plays the Cell at the given index to the Board at the given coordinates.
   * Indexes are 0 based.
   *
   * @param handIdx Index of the cell to be played from the hand.
   * @param boardX  X coordinate of the position to be played to.
   * @param boardY  Y coordinate of the position to be played to.
   * @throws IllegalArgumentException if hand index given is out of bounds.
   */
  void playCell(int handIdx, int boardX, int boardY);

  /**
   * Returns a copy of the hand of the player.
   * Mutating these cells does not change the actual cells.
   *
   * @return the hand of the player.
   */
  List<Cell> getHand();

  /**
   * Gives the color associated with the Player for setting their Cells to.
   *
   * @return The Color enum related to the player.
   */
  ThreeTrioColor getPlayerColor();
}
