package cs3500.threetrios.strategy;

import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;


/**
 * A Strategy interface for choosing where to play next for the given player.
 */
public interface ThreeTriosStrategy {

  /**
   * Chooses the best move based on the current strategy.
   * Returns a list of moves which only contains multiple moves in the event of a tie between moves
   * under this strategy.
   *
   * @param model the game model
   * @param playerColor the player for whom a move is being found
   * @return a list of moves which are best under this strategy
   * @throws IllegalArgumentException if the model or player is null
   */
  List<Move> chooseMove(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor);

  /**
   * Breaks ties between moves and returns a single move.
   *
   * @param moves a list of moves which are bust under this strategy
   * @return a single move to break the tie
   * @throws IllegalStateException if the strategy has not chosen a move yet or if there are no
   *                               open cells on the board
   */
  Move breakTies(List<Move> moves);
}
