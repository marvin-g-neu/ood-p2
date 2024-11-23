package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;

/**
 * Represents a human player.
 * The controller manages human moves.
 */
public class HumanPlayer implements Player {

  private final PlayerColor playerColor;

  /**
   * Constructs a human player.
   *
   * @param color player's color
   */
  public HumanPlayer(PlayerColor color) {
    this.playerColor = color;
  }

  @Override
  public void callbackFeatures(Actions features) {
    // Controller handles human moves
  }

  @Override
  public void getMakePlay(ThreeTriosModelInterface model) {
    // Controller handles human moves
  }

  @Override
  public PlayerColor getColor() {
    return playerColor;
  }

  @Override
  public boolean isHuman() {
    return true;
  }
}