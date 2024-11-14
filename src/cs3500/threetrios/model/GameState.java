package cs3500.threetrios.model;

/**
 * Keeps track of different variables determining
 * the state of the game in the Three Trios game
 * inside the model.
 */
public enum GameState {
  NOT_STARTED, IN_PROGRESS, RED_WIN, BLUE_WIN, EARLY_QUIT;

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isTerminal() {
    return this == RED_WIN || this == BLUE_WIN || this == EARLY_QUIT;
  }
}
