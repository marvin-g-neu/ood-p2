package cs3500.threetrios.provider.controller.gui;

import java.util.Map;

import cs3500.threetrios.provider.model.ThreeTrioColor;

/**
 * Interface for a GUI Controller of the ThreeTrioGame. Creates a controller which allows for the
 * game to be played, takes in inputs from the view being used and utilizes the model for the
 * underlying logic of the game.
 */
public interface TtGuiController {

  /**
   * Starts a game with the controller and makes the view visible. Ends when either the panel is
   * closed or the game is considered over.
   */
  void playGame();

  /**
   * Returns which player color the current turn is.
   *
   * @return the color of the player.
   */
  ThreeTrioColor getCurrentTurn();

  /**
   * Gets the map of the players to their relevant controller.
   *
   * @return A map of ThreeTrioColors to associated players.
   */
  Map<ThreeTrioColor, PlayerController> getPlayerMap();

  /**
   * Changes the turn from the current player to the next one in the list.
   */
  void changeCurrentTurn();

  /**
   * Updates all the player views.
   */
  void updateAllViews();
}
