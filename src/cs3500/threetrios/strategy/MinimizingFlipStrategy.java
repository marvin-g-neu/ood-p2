package cs3500.threetrios.strategy;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.rules.RuleKeeper;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.card.CustomCard;

import java.util.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy 3: We choose the move that minimizes the maximum number of flips 
 * the opponent can make on their next turn. For each possible move, it simulates
 * all possible opponent responses and finds the worst case (maximum flips).
 * It then selects the move that has the lowest "worst case" outcome.
 */
public class MinimizingFlipStrategy extends BasicStrategies {

  @Override
  public List<MakePlay> getBestMove(ThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and/or player cannot be null");
    }

    Map<MakePlay, Integer> moveWorstCases = new HashMap<>();
    // PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
    RuleKeeper rules = new BasicThreeTriosGame(model);

    // For each possible move we can make
    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        if (model.getGrid().getCell(row, col).isEmpty()) {
          for (int cardIdx = 0; cardIdx < model.getPlayerHand(player).size(); cardIdx++) {
            MakePlay currentMove = new MakePlay(cardIdx, row, col);
            CustomCard card = model.getPlayerHand(player).get(cardIdx);

            if (rules.isLegalMove(model.getGrid().getCell(row, col), card)) {
              // Simulate making this move and find opponent's best response
              int maxOpponentFlips = simulateOpponentResponses(model, currentMove, player);
              moveWorstCases.put(currentMove, maxOpponentFlips);
            }
          }
        }
      }
    }

    // Find moves with minimum worst-case opponent flips
    int minWorstCase = moveWorstCases.values().stream()
            .min(Integer::compareTo).orElse(Integer.MAX_VALUE);
    List<MakePlay> bestMoves = new ArrayList<>();

    for (Map.Entry<MakePlay, Integer> entry : moveWorstCases.entrySet()) {
      if (entry.getValue() == minWorstCase) {
        bestMoves.add(entry.getKey());
      }
    }

    return Collections.singletonList(breakTies(bestMoves));
  }

  /**
   * Simulates all possible opponent responses to a move and returns the maximum
   * number of flips they could achieve.
   */
  private int simulateOpponentResponses(ThreeTriosModelInterface model, 
                                      MakePlay ourMove, 
                                      PlayerColor player) {
    // Create a copy of the model to simulate moves
    ThreeTriosModelInterface simulatedModel = model.copyModel();
    simulatedModel.playTurn(ourMove.getCardInHand(), ourMove.getRow(), ourMove.getCol());
    
    PlayerColor opponent = (player == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
    RuleKeeper rules = new BasicThreeTriosGame(simulatedModel);
    int maxFlips = 0;

    // Try every possible opponent response
    for (int row = 0; row < simulatedModel.getGrid().getRows(); row++) {
      for (int col = 0; col < simulatedModel.getGrid().getCols(); col++) {
        if (simulatedModel.getGrid().getCell(row, col).isEmpty()) {
          for (int cardIdx = 0; cardIdx < simulatedModel.getPlayerHand(opponent).size(); cardIdx++) {
            CustomCard opponentCard = simulatedModel.getPlayerHand(opponent).get(cardIdx);
            
            if (rules.isLegalMove(simulatedModel.getGrid().getCell(row, col), opponentCard)) {
              int flips = rules.getPotentialFlips(row, col, cardIdx, opponent);
              maxFlips = Math.max(maxFlips, flips);
            }
          }
        }
      }
    }

    return maxFlips;
  }
}
