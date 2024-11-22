package cs3500.threetrios.view;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.model.PlayerColor;

/**
 * Interface for the GUI view of the Three Trios game.
 */
public interface ThreeTriosGraphicalView {

  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Refreshes the view to reflect the current state of the model.
   */
  void refresh();

  /**
   * Sets the feature callbacks (controller) to handle user actions.
   *
   * @param features the features (controller) to handle actions
   */
  void setFeatures(Features features);

  /**
   * Resets the selected card in the view.
   */
  void resetSelection(PlayerColor player);

  /**
   * Displays a message to the user.
   * @param str the message to be displayed
   */
  void showMessage(String str);
}
