package cs3500.threetrios.provider.model.players.ai.strategies;

import java.util.List;

import cs3500.threetrios.provider.model.players.ai.AiParams;

/**
 * The interface for strategy, every strategy must have the logic to pick
 * a list of the best moves.
 */
public interface Strategy {
  /**
   * The logic for playing a move.
   *
   * @param options the available options.
   * @return the list of the best options based on the strategy.
   */
  List<AiParams> chooseMove(List<AiParams> options);
}
