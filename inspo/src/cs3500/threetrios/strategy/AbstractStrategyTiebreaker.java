package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for tie-breaking behavior in implementations of ThreeTriosStrategy.
 */
abstract class AbstractStrategyTiebreaker implements ThreeTriosStrategy {
  @Override
  public Move breakTies(List<Move> moves) {
    if (moves.size() == 1) {
      return moves.get(0);
    }
    // gets uppermost moves
    Map<Move, Integer> rowMap = new HashMap<>();
    for (Move move : moves) {
      rowMap.put(move, move.getRow());
    }
    List<Move> uppermostMoves = new ArrayList<>();
    for (Move move : rowMap.keySet()) {
      if (move.getRow() == Collections.min(rowMap.values())) {
        uppermostMoves.add(move);
      }
    }
    if (uppermostMoves.size() == 1) {
      return uppermostMoves.get(0);
    }
    // gets leftmost moves
    Map<Move, Integer> colMap = new HashMap<>();
    for (Move move : uppermostMoves) {
      colMap.put(move, move.getCol());
    }
    List<Move> leftmostMoves = new ArrayList<>();
    for (Move move : colMap.keySet()) {
      if (move.getCol() == Collections.min(colMap.values())) {
        leftmostMoves.add(move);
      }
    }
    if (leftmostMoves.size() == 1) {
      return leftmostMoves.get(0);
    }
    // gets move with index closest to 0 in hand
    Map<Move, Integer> cardInHandIdxMap = new HashMap<>();
    for (Move move : leftmostMoves) {
      cardInHandIdxMap.put(move, move.getCardIdxInHand());
    }
    for (Move move : cardInHandIdxMap.keySet()) {
      if (move.getCardIdxInHand() == Collections.min(cardInHandIdxMap.values())) {
        return move;
      }
    }
    return null;
  }
}
