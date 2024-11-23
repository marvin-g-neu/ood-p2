package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;

/**
 * Represents a game player.
 */
public interface Player {
  /**
   * Registers controller actions.
   *
   * @param features controller's action handlers
   */
  void callbackFeatures(Actions features);

  /**
   * Executes the player's move.
   *
   * @param model current game state
   */
  void getMakePlay(ThreeTriosModelInterface model);

  /**
   * Checks if player is human.
   *
   * @return true if human, false otherwise
   */
  boolean isHuman();

  /**
   * Gets the player's color.
   *
   * @return player's color
   */
  PlayerColor getColor();  
}
