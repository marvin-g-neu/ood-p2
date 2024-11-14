package cs3500.threetrios.strategy;

/**
 * Class that contains all parameters required for playing a card to the board.
 */
public class MakePlay {
  private final int cardInHand;
  private final int row;
  private final int col;

  /**
   * Creates a new play with the coordinates and card in hand index for the move.
   *
   * @param cardIdxInHand card in hand index to play to the board
   * @param row           row index of the cell to play the card to
   * @param col           column index of the cell to play the card to
   */
  public MakePlay(int cardIdxInHand, int row, int col) {
    this.cardInHand = cardIdxInHand;
    this.row = row;
    this.col = col;
  }

  public int getCardInHand() {
    return cardInHand;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    // Check if the objects are the same
    if (this == obj) return true;
    // Check if the object is an instance of MakePlay
    if (!(obj instanceof MakePlay)) return false;
    // Cast and compare the fields
    MakePlay other = (MakePlay) obj;
    return this.cardInHand == other.cardInHand &&
        this.row == other.row &&
        this.col == other.col;
  }

  @Override
  public int hashCode() {
    // Hash the fields and sum them
    int result = Integer.hashCode(cardInHand);
    result += Integer.hashCode(row);
    result += Integer.hashCode(col);
    return result;
  }
}
