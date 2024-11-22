package cs3500.threetrios.controller.players;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.controller.Actions;
import cs3500.threetrios.strategy.MakePlay;
import cs3500.threetrios.strategy.Strategy;
/**
 * Computer player for a game of Three Trios. 
 * This class will select the best move according to the strategy.
 */
public class ComputerPlayer implements Player {

  private final PlayerColor playerColor;
  private final Strategy strategy;
  private Actions features;

  /**
   * Constructor for a new computer player.
   *
   * @param color the player's color in the game (Red / Blue)
   * @param strategy the strategy to use for this computer player
   */
  public ComputerPlayer(PlayerColor color, Strategy strategy) {
    this.playerColor = color;
    this.strategy = strategy;
  }

  @Override
  public void callbackFeatures(Actions features) {
    // store the features for use making moves
    this.features = features;
  }

  @Override 
  public void getMakePlay(ThreeTriosModelInterface model) {
    // get the best move according to the strategy choosen
    MakePlay bestMove = this.strategy.getBestMove(model, this.playerColor);
    // select the card and cell for the move to be made
    features.selectCard(playerColor.toString(), bestMove.getCardInHand());
    features.selectCell(bestMove.getRow(), bestMove.getCol());
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