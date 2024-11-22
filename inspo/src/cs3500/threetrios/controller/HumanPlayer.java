package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Human player for a game of Three Trios.
 * The view will emit player actions for human players, not this class.
 */
public class HumanPlayer implements Player {

  private final PlayerColor playerColor;

  /**
   * Creates a new human player.
   *
   * @param color the player's color in the game (Red / Blue)
   */
  public HumanPlayer(PlayerColor color) {
    this.playerColor = color;
  }

  @Override
  public void getMove(ReadonlyThreeTriosModel<?> model) {
    // done by view, not this.
  }

  @Override
  public PlayerColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public void setFeatures(Features features) {
    // Stub
  }

  @Override
  public boolean isComputer() {
    return false;
  }
}
