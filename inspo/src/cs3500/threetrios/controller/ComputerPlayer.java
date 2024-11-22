package cs3500.threetrios.controller;

import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.strategy.Move;
import cs3500.threetrios.strategy.ThreeTriosStrategy;

/**
 * Computer player for a game of Three Trios.
 * Computer players emit player actions to the controller.
 * The computer player chooses a move based on a game strategy.
 */
public class ComputerPlayer implements Player {
  private final PlayerColor playerColor;
  private final ThreeTriosStrategy strategy;
  private Features features;

  /**
   * Creates a new computer player.
   *
   * @param playerColor the player's color
   * @param strategy    the player's strategy
   */
  public ComputerPlayer(PlayerColor playerColor, ThreeTriosStrategy strategy) {
    this.playerColor = playerColor;
    this.strategy = strategy;
  }

  @Override
  public void getMove(ReadonlyThreeTriosModel<?> model) {
    List<Move> moves = strategy.chooseMove(model, playerColor);
    Move bestMove = strategy.breakTies(moves);
    features.selectCard(playerColor.toString(), bestMove.getCardIdxInHand());
    features.clickCell(bestMove.getRow(), bestMove.getCol());
  }

  @Override
  public PlayerColor getPlayerColor() {
    return playerColor;
  }

  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public boolean isComputer() {
    return true;
  }
}
