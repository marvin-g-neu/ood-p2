package cs3500.threetrios.strategy;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;

import java.util.List;

/**
 * Strategy interface for selecting moves in ThreeTrios.
 */
public interface Strategy {
  /**
   * Selects a move based on the strategy.
   *
   * @param model  the current game model
   * @param player the player for whom the move is being selected
   * @return the selected move
   * @throws IllegalArgumentException if parameters are null
   * @throws IllegalStateException    if game not in progress
   * @throws IllegalArgumentException if player has no move
   */
  MakePlay getBestMove(ThreeTriosModelInterface model, PlayerColor player);

  /**
   * Breaks ties between moves.
   *
   * @param plays the list of moves to break ties between
   * @return the move to make
   * @throws IllegalArgumentException if elements in plays are null
   */
  MakePlay breakTies(List<MakePlay> plays);
} 

