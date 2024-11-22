package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;


/**
 * Strategy: find the move that flips the most cards.
 * The strategy returns null if the player cannot play to any cell.
 */
public class FlipMostCards extends AbstractStrategyTiebreaker {

  private boolean moveChosen = false;

  @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
  @Override
  public List<Move> chooseMove(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor) {
    if (model == null || playerColor == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }
    Map<Move, Integer> scoreMap = new HashMap<>();
    List<Move> mostFlipsMoves = new ArrayList<>();
    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridWidth(); col++) {
        if (model.legalPlayToCell(row, col)) {
          for (int cardIdxInHand = 0; cardIdxInHand < model.getHand(playerColor).size();
               cardIdxInHand++) {
            scoreMap.put(new Move(row, col, cardIdxInHand),
                    model.numCardsFlippedWhenPlayed(row, col, cardIdxInHand));
          }
        }
      }
    }
    moveChosen = true;
    if (scoreMap.isEmpty()) { // cannot play to any cell, no valid moves
      return null;
    }
    int maxFlips = Collections.max(scoreMap.values());
    for (Move move : scoreMap.keySet()) {
      if (scoreMap.get(move) == maxFlips) {
        mostFlipsMoves.add(move);
      }
    }
    return mostFlipsMoves;
  }

  @Override
  public Move breakTies(List<Move> moves) {
    if (!moveChosen) {
      throw new IllegalStateException("Strategy has not chosen a move yet");
    }
    if (moves == null) {
      throw new IllegalStateException("No open cells in grid");
    }
    return super.breakTies(moves);
  }
}
