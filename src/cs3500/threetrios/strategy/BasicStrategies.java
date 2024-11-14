package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

/**
 * Abstract class that implements the Strategy interface for basic
 * strategies which use the breakTies method to break ties among a list of plays
 * as with the uppermost-leftmost coordinate for the position and then choose
 * the best card for that position with an index closest to 0 in the hand.
 * Note if there is no valid move we choose the upper-most, left-most open
 * position and the card at index 0.
 */
public abstract class BasicStrategies implements Strategy {
  /**
   * Breaks ties among a list of plays based on predefined criteria.
   *
   * @param plays the list of tied plays
   * @return the play selected after tie-breaking
   */
  @Override
  public MakePlay breakTies(List<MakePlay> plays) {
    for (MakePlay play : plays) {
      if (play == null) {
        throw new IllegalArgumentException("Play cannot be null");
      }
    }

    // If there is only one play, return it
    if (plays.size() == 1) {
      return plays.get(0);
    }

    // Find uppermost plays using a Map
    HashMap<MakePlay, Integer> rowMap = new HashMap<>();
    for (MakePlay play : plays) {
      rowMap.put(play, play.getRow());
    }

    // Find the plays with the minimum row index
    List<MakePlay> uppermostPlays = new ArrayList<>();
    int minRow = Collections.min(rowMap.values());
    for (MakePlay play : rowMap.keySet()) {
      if (play.getRow() == minRow) {
        uppermostPlays.add(play);
      }
    }

    // If there is only one uppermost play, return it
    if (uppermostPlays.size() == 1) {
      return uppermostPlays.get(0);
    }

    // Find leftmost plays among uppermost plays
    HashMap<MakePlay, Integer> colMap = new HashMap<>();
    for (MakePlay play : uppermostPlays) {
      colMap.put(play, play.getCol());
    }

    // Find the plays with the minimum column index
    List<MakePlay> leftmostPlays = new ArrayList<>();
    int minCol = Collections.min(colMap.values());
    for (MakePlay play : colMap.keySet()) {
      if (play.getCol() == minCol) {
        leftmostPlays.add(play);
      }
    }

    // If there is only one leftmost play, return it
    if (leftmostPlays.size() == 1) {
      return leftmostPlays.get(0);
    }

    // Find play with lowest card index in hand
    HashMap<MakePlay, Integer> cardIdxMap = new HashMap<>();
    for (MakePlay play : leftmostPlays) {
      cardIdxMap.put(play, play.getCardInHand());
    }

    // Find the plays with the minimum card index in hand
    int minCardIdx = Collections.min(cardIdxMap.values());
    for (MakePlay play : cardIdxMap.keySet()) {
      if (play.getCardInHand() == minCardIdx) {
        return play;
      }
    }

    // If no valid play is found, return the first leftmost play
    return leftmostPlays.get(0);
  }
}
