package cs3500.threetrios.view;

import cs3500.threetrios.controller.GameListeners;

/**
 * A view interface specific to a GUI view of Three Trios. For anything that
 * shouldn't be modifying the view, the base ThreeTriosView should be used.
 */
public interface ThreeTriosGUIViewInterface extends ThreeTriosView {
  /**
   * Set the frame to be visible or not visible.
   *
   * @param visible whether the frame should be visible or not
   */
  void setVisible(boolean visible);

  /**
   * Display a message on the GUI view.
   *
   * @param message the message to display
   */
  void displayMessage(String message);

  /**
   * Set the paired controller for this view.
   *
   * @param controller the paired controller
   */
  void setController(GameListeners controller);
}
