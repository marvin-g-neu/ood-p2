package cs3500.threetrios.strategy;

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
 * Strategy 1: Find the move that flips the most cards.
 * Returns an empty list if the player cannot play to any cell.
 * Uses breakTies from BasicStrategies for tie-breaking.
 */
public class MaxFlipsStrategy extends BasicStrategies {

  @Override
  public MakePlay getBestMove(ThreeTriosModelInterface model, PlayerColor player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and/or player cannot be null");
    }

    Map<MakePlay, Integer> flipCounts = calculateFlipCounts(model, player);

    // If no valid moves are found, choose the uppermost,
    // left-most open position and the card at index 0
    if (flipCounts.isEmpty()) {
      int minRow = Integer.MAX_VALUE;
      int minCol = Integer.MAX_VALUE;
      for (int row = 0; row < model.getGrid().getRows(); row++) {
        for (int col = 0; col < model.getGrid().getCols(); col++) {
          Cell cell = model.getGrid().getCell(row, col);
          if (cell.isEmpty() && row < minRow) {
            minRow = row;
            minCol = col;
          }
        }
      }
      if (minRow != Integer.MAX_VALUE) {
        return new MakePlay(0, minRow, minCol);
      }
      return new MakePlay(0, 0, 0);
    }

    List<MakePlay> bestMoves = findBestMoves(flipCounts);

    // Use breakTies from superclass and return as single-element list
    return breakTies(bestMoves);
  }

  // Calculates the number of flips for each possible move
  private Map<MakePlay, Integer> calculateFlipCounts(ThreeTriosModelInterface model,
                                                     PlayerColor player) {
    Map<MakePlay, Integer> flipCounts = new HashMap<>();
    RuleKeeper rules = new BasicThreeTriosGame(model);

    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        Cell cell = model.getGrid().getCell(row, col);
        for (int cardIdx = 0; cardIdx < model.getPlayerHand(player).size(); cardIdx++) {
          ThreeTriosCard card = (ThreeTriosCard) model.getPlayerHand(player).get(cardIdx);
          if (rules.isLegalMove(cell, card)) {
            int potentialFlips = rules.getPotentialFlips(row, col, cardIdx, player);
            MakePlay move = new MakePlay(cardIdx, row, col);
            flipCounts.put(move, potentialFlips);
          }
        }
      }
    }
    return flipCounts;
  }

  // Finds the moves with the maximum number of flips
  private List<MakePlay> findBestMoves(Map<MakePlay, Integer> flipCounts) {
    int maxFlips = Collections.max(flipCounts.values());
    List<MakePlay> bestMoves = new ArrayList<>();

    for (Map.Entry<MakePlay, Integer> entry : flipCounts.entrySet()) {
      if (entry.getValue() == maxFlips) {
        bestMoves.add(entry.getKey());
      }
    }
    return bestMoves;
  }
}
