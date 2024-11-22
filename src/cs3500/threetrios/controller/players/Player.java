package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;

/**
* Represents a player in the Three Trios game. Players can be either human or computer-controlled,
* and are responsible for making moves on their turn. Each player has an assigned color (RED or BLUE)
* and can interact with the game through feature callbacks.
*/
public interface Player {
  /**
   * Registers the feature callbacks from the controller that will handle player actions.
   * These callbacks allow the player to communicate their moves back to the game.
   *
 * @param features The controller's feature interface containing action handlers
   */
  void callbackFeatures(Actions features);

  /**
   * Determines and executes the player's next move using the current game state.
   * For human players, this triggers input handling. For computer players, this
   * calculates and executes the best move according to their strategy.
   *
   * @param model A read-only view of the game state to base the move on
   */
   void getMakePlay(ThreeTriosModelInterface model);

  /**
   * Identifies whether this player is human-controlled or computer-controlled.
   * This distinction affects how moves are determined and executed.
   *
   * @return true if this is a human player, false if this is a computer player
   */
  boolean isHuman();

  /**
   * Retrieves this player's assigned color in the game.
   * The color (RED or BLUE) identifies the player and their pieces on the board.
   *
   * @return The PlayerColor enum value representing this player's color
   */
  PlayerColor getColor();  
}
