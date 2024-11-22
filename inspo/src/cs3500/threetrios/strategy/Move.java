package cs3500.threetrios.strategy;

/**
 * Class that contains all parameters required for playing a card to the board.
 */
public class Move {
  private final int row;
  private final int col;
  private final int cardIdxInHand;

  /**
   * Creates a new move with the coordinates and card in hand index for the move.
   *
   * @param row row index
   * @param col column index
   * @param cardIdxInHand card in hand index
   */
  public Move(int row, int col, int cardIdxInHand) {
    this.row = row;
    this.col = col;
    this.cardIdxInHand = cardIdxInHand;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getCardIdxInHand() {
    return cardIdxInHand;
  }
}
