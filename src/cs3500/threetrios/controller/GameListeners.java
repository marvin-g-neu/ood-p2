package cs3500.threetrios.controller;

/**
 * Represents model and view functionalities.
 */
public interface GameListeners {
  
  /**
   * Refreshes the view screen.
   */
  void refreshScreen();

  /**
   * Makes a new screen visible.
   */
  void makeScreenVisible();

  /**
   * Runs the current player's turn.
   */
  void runPlayerTurn();

  /**
   * Executes actions when the game ends.
   */
  void runGameOver();
}