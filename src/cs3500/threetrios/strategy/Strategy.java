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
   */
  List<MakePlay> getBestMove(ThreeTriosModelInterface model, PlayerColor player);

  /**
   * Breaks ties between moves.
   *
   * @param moves the list of moves to break ties between
   * @return the move to make
   */
  MakePlay breakTies(List<MakePlay> plays);
} 

