package cs3500.threetrios.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;


/**
 * Strategy: Play a card to one of the corners, as it exposes only 2 of its attack values, making
 * it harder to flip.
 * The strategy returns the move with the uppermost-leftmost open cell as the position and the
 * card at index 0 in the hand if the corners cannot be played to.
 */
public class GoForTheCorners extends AbstractStrategyTiebreaker {

  private ReadonlyThreeTriosModel<?> model;

  @Override
  public List<Move> chooseMove(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor) {
    if (model == null || playerColor == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }
    this.model = model;
    Map<Move, Double> avgAtkValueMap = new HashMap<>();
    if (!model.legalPlayToCell(0, 0) && !model.legalPlayToCell(0, model.getGridWidth() - 1)
            && !model.legalPlayToCell(model.getGridHeight() - 1, 0)
            && !model.legalPlayToCell(model.getGridHeight() - 1, model.getGridWidth() - 1)) {
      return null; // corners cannot be played to
    }
    for (int cardInHandIdx = 0; cardInHandIdx < model.getHand(playerColor).size();
         cardInHandIdx++) {
      avgAtkValueMap.put(new Move(0, 0, cardInHandIdx),
              getTopLeftScore(model, playerColor, cardInHandIdx));
      avgAtkValueMap.put(new Move(0, model.getGridWidth() - 1, cardInHandIdx),
              getTopRightScore(model, playerColor, cardInHandIdx));
      avgAtkValueMap.put(new Move(model.getGridHeight() - 1, 0, cardInHandIdx),
              getBottomLeftScore(model, playerColor, cardInHandIdx));
      avgAtkValueMap.put(new Move(model.getGridHeight() - 1, model.getGridWidth() - 1,
              cardInHandIdx), getBottomRightScore(model, playerColor, cardInHandIdx));
    }
    List<Move> hardestToFlip = new ArrayList<>();
    for (Move move : avgAtkValueMap.keySet()) {
      if (Objects.equals(avgAtkValueMap.get(move), Collections.max(avgAtkValueMap.values()))) {
        hardestToFlip.add(move);
      }
    }
    return hardestToFlip;
  }

  /**
   * Returns the average of the exposed attack values for a card played in the top left cell
   * on the board.
   * If there are no exposed attack values (both adjacent cells cannot be played to),
   * this method returns 10. This is equal to the returned value when a card has an A for both
   * of its exposed values. In both scenarios, the card cannot be flipped.
   * This method returns 0 if the top-left cell cannot be played to.
   *
   * @param model the game model
   * @param playerColor the player for whom a move is being found
   * @param cardInHandIdx the index in the hand for the card which is being evaluated
   * @return the average of the exposed attack values (if applicable)
   */
  private double getTopLeftScore(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor,
                                 int cardInHandIdx) {
    if (model.legalPlayToCell(0, 0)) {
      int openAdjacentCells = 0;
      int relevantAtkVals = 0;
      if (model.legalPlayToCell(0, 1)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getEast();
      }
      if (model.legalPlayToCell(1, 0)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getSouth();
      }
      return getAvgExposedValue(openAdjacentCells, relevantAtkVals);
    }
    return 0;
  }

  /**
   * Returns the average of the exposed attack values for a card played in the top right cell
   * on the board.
   * If there are no exposed attack values (both adjacent cells cannot be played to),
   * this method returns 10. This is equal to the returned value when a card has an A for both
   * of its exposed values. In both scenarios, the card cannot be flipped.
   * This method returns 0 if the top-right cell cannot be played to.
   *
   * @param model the game model
   * @param playerColor the player for whom a move is being found
   * @param cardInHandIdx the index in the hand for the card which is being evaluated
   * @return the average of the exposed attack values (if applicable)
   */
  private double getTopRightScore(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor,
                                 int cardInHandIdx) {
    if (model.legalPlayToCell(0, model.getGridWidth() - 1)) {
      int openAdjacentCells = 0;
      int relevantAtkVals = 0;
      if (model.legalPlayToCell(0, model.getGridWidth() - 2)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getWest();
      }
      if (model.legalPlayToCell(1, model.getGridHeight() - 1)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getSouth();
      }
      return getAvgExposedValue(openAdjacentCells, relevantAtkVals);
    }
    return 0;
  }

  /**
   * Returns the average of the exposed attack values for a card played in the bottom left cell
   * on the board.
   * If there are no exposed attack values (both adjacent cells cannot be played to),
   * this method returns 10. This is equal to the returned value when a card has an A for both
   * of its exposed values. In both scenarios, the card cannot be flipped.
   * This method returns 0 if the bottom-left cell cannot be played to.
   *
   * @param model the game model
   * @param playerColor the player for whom a move is being found
   * @param cardInHandIdx the index in the hand for the card which is being evaluated
   * @return the average of the exposed attack values (if applicable)
   */
  private double getBottomLeftScore(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor,
                                 int cardInHandIdx) {
    if (model.legalPlayToCell(model.getGridHeight() - 1, 0)) {
      int openAdjacentCells = 0;
      int relevantAtkVals = 0;
      if (model.legalPlayToCell(model.getGridHeight() - 1, 1)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getEast();
      }
      if (model.legalPlayToCell(model.getGridHeight() - 2, 0)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getNorth();
      }
      return getAvgExposedValue(openAdjacentCells, relevantAtkVals);
    }
    return 0;
  }

  /**
   * Returns the average of the exposed attack values for a card played in the bottom right cell
   * on the board.
   * If there are no exposed attack values (both adjacent cells cannot be played to),
   * this method returns 10. This is equal to the returned value when a card has an A for both
   * of its exposed values. In both scenarios, the card cannot be flipped.
   * This method returns 0 if the bottom-right cell cannot be played to.
   *
   * @param model the game model
   * @param playerColor the player for whom a move is being found
   * @param cardInHandIdx the index in the hand for the card which is being evaluated
   * @return the average of the exposed attack values (if applicable)
   */
  private double getBottomRightScore(ReadonlyThreeTriosModel<?> model, PlayerColor playerColor,
                                 int cardInHandIdx) {
    if (model.legalPlayToCell(model.getGridHeight() - 1, model.getGridWidth() - 1)) {
      int openAdjacentCells = 0;
      int relevantAtkVals = 0;
      if (model.legalPlayToCell(model.getGridHeight() - 1, model.getGridWidth() - 2)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getWest();
      }
      if (model.legalPlayToCell(model.getGridHeight() - 2, model.getGridWidth() - 1)) {
        openAdjacentCells++;
        relevantAtkVals += model.getHand(playerColor).get(cardInHandIdx).getNorth();
      }
      return getAvgExposedValue(openAdjacentCells, relevantAtkVals);
    }
    return 0;
  }

  /**
   * Helper method for getting the average exposed attack value based on the sum of the exposed
   * values and the number of values summed.
   *
   * @param openAdjacentCells the number of open adjacent cells
   * @param relevantAtkVals the sum of the exposed attack values
   * @return the average exposed attack value
   */
  private double getAvgExposedValue(int openAdjacentCells, int relevantAtkVals) {
    switch (openAdjacentCells) {
      case 0: return 10;
      case 1: return relevantAtkVals;
      case 2: return (double) relevantAtkVals / 2;
      default: return 0;
    }
  }

  @Override
  public Move breakTies(List<Move> moves) {
    if (model == null) {
      throw new IllegalStateException("Strategy has not chosen a move yet");
    }
    if (moves == null) {
      for (int row = 0; row < model.getGridHeight(); row++) {
        for (int col = 0; col < model.getGridWidth(); col++) {
          if (model.legalPlayToCell(row, col)) {
            return new Move(row, col, 0);
          }
        }
      }
      throw new IllegalStateException("No open cells in grid");
    }
    return super.breakTies(moves);
  }
}
