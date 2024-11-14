package cs3500.threetrios.strategy;

import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.rules.RuleKeeper;
import cs3500.threetrios.model.rules.BasicThreeTriosGame;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strategy: Play a card to one of the corners, as it exposes only 2 of its attack values,
 * making it harder to flip. If corners cannot be played to, choose the uppermost-leftmost
 * open cell and the card at index 0 in the hand.
 */
public class CornerStrategy extends BasicStrategies {
  @Override
  public List<MakePlay> getBestMove(ThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (model.getGameState() != GameState.IN_PROGRESS) {
      throw new IllegalStateException("Game state is not in progress");
    }

    Map<MakePlay, Integer> cornerMoves = calculateCornerMoves(model, player);

    if (cornerMoves.isEmpty()) {
      return new ArrayList<>();
    }

    List<MakePlay> bestMoves = findBestCornerMoves(cornerMoves);

    // Use breakTies from superclass and return as single-element list
    return Collections.singletonList(breakTies(bestMoves));
  }

  // Calculates the number of flips for each possible move to a corner
  private Map<MakePlay, Integer> calculateCornerMoves(ThreeTriosModelInterface model,
                                                      PlayerColor player) {
    Map<MakePlay, Integer> cornerMoves = new HashMap<>();
    RuleKeeper rules = new BasicThreeTriosGame(model);
    int maxRow = model.getGrid().getRows() - 1;
    int maxCol = model.getGrid().getCols() - 1;

    // Define corner positions
    int[][] corners = {
        {0, 0}, {0, maxCol},
        {maxRow, 0}, {maxRow, maxCol}
    };

    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];
      Cell cell = model.getGrid().getCell(row, col);

      if (cell.isEmpty()) {
        for (int cardIdx = 0; cardIdx < model.getPlayerHand(player).size(); cardIdx++) {
          ThreeTriosCard card = (ThreeTriosCard) model.getPlayerHand(player).get(cardIdx);
          if (rules.isLegalMove(cell, card)) {
            // Calculate how many opponent cards could potentially flip this card
            int vulnerability = rules.getPotentialFlips(row, col, cardIdx, player);
            MakePlay move = new MakePlay(cardIdx, row, col);
            cornerMoves.put(move, -vulnerability); // Negative because lower is better
          }
        }
      }
    }
    return cornerMoves;
  }

  // Finds the moves with the minimum vulnerability (maximum negative value)
  private List<MakePlay> findBestCornerMoves(Map<MakePlay, Integer> cornerMoves) {
    int bestScore = Collections.max(cornerMoves.values());
    List<MakePlay> bestMoves = new ArrayList<>();

    for (Map.Entry<MakePlay, Integer> entry : cornerMoves.entrySet()) {
      if (entry.getValue() == bestScore) {
        bestMoves.add(entry.getKey());
      }
    }
    return bestMoves;
  }
}
