package cs3500.threetrios.provider.model;

import java.util.List;

import cs3500.threetrios.provider.model.players.ai.StrategyEnum;

/**
 * The interface for a gridBuilder where it can start a ThreeTrioGameModel
 * with specific deck and board inputs by reading files.
 */
public interface GridBuilder {
  /**
   * Method used to set a Grid for the ThreeTrio Game from a given File.
   *
   * @param filename The file from which the grid is read.
   * @return The created GridBuilder object made from the file.
   * @throws IllegalArgumentException if filename is null.
   * @throws IllegalArgumentException if it fails to read the file.
   * @throws IllegalArgumentException if the file is not formatted correctly for the Grid.
   */
  GridBuilder setGrid(String filename);

  /**
   * Method used to set Cells into a Grid for the ThreeTrio Game from a given File.
   *
   * @param filename The file from which the cells are meant to be setup.
   * @return GridBuilder object with cells added to it.
   * @throws IllegalArgumentException if filename is null.
   * @throws IllegalArgumentException if a cell cannot make a Cell object from
   *                                  the structure found in the file given.
   * @throws IllegalArgumentException if the file gives an IOException.
   */
  GridBuilder setCells(String filename);

  /**
   * Starts the game in the inputted model with the inputted grids and cells.
   *
   * @param model   the model to make playable.
   * @param shuffle if the deck should be shuffled before playing.
   * @return the model that was just set to play.
   * @throws IllegalArgumentException if either the Grid or Cells are null.
   */
  ThreeTrioModel play(ThreeTrioModel model, boolean shuffle);

  /**
   * Sets the player to the request specifications. If a human player then the StrategyEnum is set
   * to null. Else creates an AI that utilizes the strategy.
   *
   * @param color   Color of the Player being created.
   * @param enumVal Enum to determine the strategy to be used.
   * @return this.
   */
  GridBuilder setupPlayers(ThreeTrioColor color, List<StrategyEnum> enumVal);

  /**
   * Sets num amount of the players to a strategy value.
   *
   * @param enumVal the strategy
   * @param num     the number of player to be set.
   */
  GridBuilder massSetPlayers(List<StrategyEnum> enumVal, int num);
}
