package cs3500.threetrios.provider.view.gui;

import cs3500.threetrios.provider.controller.gui.PlayerController;
import cs3500.threetrios.provider.model.ThreeTrioColor;

/**
 * Interface for a GUI View of the Three Trio Game. Takes in inputs from a board and relays them
 * to a relevant TTGUIController to allow for play of a ThreeTrioGame.
 */
public interface TtGuiView {

  /**
   * Sets the players of the left and right panels to colorLeft and colorRight.
   *
   * @param colorLeft  the left color.
   * @param colorRight the right color.
   */
  void setPanelColors(ThreeTrioColor colorLeft, ThreeTrioColor colorRight);

  /**
   * Sets up the controller to handle click events in this view.
   *
   * @param listener the controller.
   */
  void addClickListener(PlayerController listener);

  /**
   * Refreshes the hand panel to reflect any changes in the game state.
   */
  void refreshPanels();

  /**
   * Refreshes the board to reflect any changes in the game state.
   */
  void refreshBoard();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * displays an error through a popup box using the string provided.
   *
   * @param message the message of the error.
   */
  void throwError(String message);

  /**
   * Sends a popup to the user of the scores of the game.
   */
  void showScoresPopup();
}
