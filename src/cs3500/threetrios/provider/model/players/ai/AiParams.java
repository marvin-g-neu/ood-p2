package cs3500.threetrios.provider.model.players.ai;

import java.awt.Point;

/**
 * Stores the values of a possible AI move. Stores the position to be played to and the index of
 * the cell in the hand of the AI.
 */
public class AiParams {
  private final Point pos;
  private final int cellIdx;

  /**
   * Constructor for the AIParam, takes in a Point and an int to represent the position to play
   * to and the position of the cell in hand.
   *
   * @param pos The position at which the AI will play to.
   * @param idx The index of the cell in the hand of the AI.
   */
  public AiParams(Point pos, int idx) {
    this.pos = pos;
    this.cellIdx = idx;
  }

  /**
   * Gets the Point at which the AI will play.
   *
   * @return The Point on the board where the AI will play.
   */
  public Point getPos() {
    return pos;
  }

  /**
   * Gets the index of the Cell in the hand the AI has chosen.
   *
   * @return the index of the Cell in the hand that the AI wants to play.
   */
  public int getIdx() {
    return cellIdx;
  }
}
