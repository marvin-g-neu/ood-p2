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
  void setScreenVisible(boolean visible);

  /**
   * Runs the current player's turn.
   */
  void runPlayerTurn();

  /**
   * Executes actions when the game ends.
   */
  void runGameOver();

  /**
   * Play the card at the index in hand to the given coordinates and then pass the turn.
   *
   * @param row       the row of the position to play
   * @param col       the column of the position to play
   * @param handIndex the index in hand of the card to be played
   */
  void playMove(int row, int col, int handIndex);
}