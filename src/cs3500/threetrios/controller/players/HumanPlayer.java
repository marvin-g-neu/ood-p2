package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;
/**
 * Human player for a game of Three Trios.
 * The view will emit player actions for human players, not this class.
 */
public class HumanPlayer implements Player {

  private final PlayerColor playerColor;

  /**
   * Constructor for a new human player.
   *
   * @param color the player's color in the game (Red / Blue)
   */
  public HumanPlayer(PlayerColor color) {
    this.playerColor = color;
  }

  @Override
  public void callbackFeatures(Actions features) {
    // controller handles human moves
  }

  @Override
  public void getMakePlay(ThreeTriosModelInterface model) {
    // controller handles human moves
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