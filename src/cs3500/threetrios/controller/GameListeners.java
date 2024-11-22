package cs3500.threetrios.controller;

/**
 * The model features interface represents the functionality 
 * controlled by the model and view in the MVC architecture for Three Trios.
 */
public interface GameListeners {
  
  /**
   * Makes the view refresh in the controller.
   */
  void refreshScreen();

  /**
   * Makes a new visible in the controller.
   */
  void makeScreenVisible();

  /**
   * Makes the controller run the actions for the 
   * now current player's turn.
   */
  void runPlayerTurn();

  /**
   * Makes the controller run the actions for 
   * when the game is over.
   */
  void runGameOver();
}