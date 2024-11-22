package cs3500.threetrios.controller;

/**
 * Interface for features for the model in Three Trios.
 */
public interface ModelFeatures {

  /**
   * Notifies the current player that it is their turn.
   */
  void notifyCurrentPlayer();

  /**
   * Notifies the controller that the game is over.
   */
  void notifyGameOver();

  /**
   * Tells the controller to refresh the view.
   */
  void refreshView();

  /**
   * Tells the controller to make the view visible.
   */
  void makeVisible();
}
