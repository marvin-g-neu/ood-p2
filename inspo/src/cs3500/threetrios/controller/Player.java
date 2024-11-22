package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Player interface for Three Trios.
 */
public interface Player {
  /**
   * Gets a move from the player.
   *
   * @param model the model from which a move will be found
   */
  void getMove(ReadonlyThreeTriosModel<?> model);

  /**
   * Returns the player's color: Red or Blue.
   *
   * @return the player's color
   */
  PlayerColor getPlayerColor();

  /**
   * Sets the feature callbacks (controller) to handle user actions.
   *
   * @param features the features (controller) to handle actions
   */
  void setFeatures(Features features);

  /**
   * Determines if the player is a computer or not.
   *
   * @return true if the player is a computer player, false if a human
   */
  boolean isComputer();
}
