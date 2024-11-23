package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;
import cs3500.threetrios.strategy.MakePlay;
import cs3500.threetrios.strategy.Strategy;

/**
 * Represents a computer-controlled player.
 */
public class ComputerPlayer implements Player {

  private final PlayerColor playerColor;
  private final Strategy strategy;
  private Actions features;

  /**
   * Constructs a computer player.
   *
   * @param color player's color
   * @param strategy strategy to choose moves
   */
  public ComputerPlayer(PlayerColor color, Strategy strategy) {
    this.playerColor = color;
    this.strategy = strategy;
  }

  @Override
  public void callbackFeatures(Actions features) {
    // Store controller actions
    this.features = features;
  }

  @Override 
  public void getMakePlay(ThreeTriosModelInterface model) {
    // Get best move from strategy
    MakePlay bestMove = this.strategy.getBestMove(model, this.playerColor);
    // Select card and cell
    boolean canPlay = features.selectCard(playerColor.toString(), bestMove.getCardInHand());
    if (canPlay) {
      features.selectCell(bestMove.getRow(), bestMove.getCol());
    }
  }

  @Override
  public PlayerColor getColor() {
    return playerColor;
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}