package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;

/**
 * An implementation of a cell from ThreeTrios.
 */
public class ThreeTriosCell implements Cell {
  private CustomCard card;
  private CellState cellState;

  /**
   * Creates a cell which is either a hole or empty.
   *
   * @param isHole if true, cellState is hole, otherwise it is empty
   */
  public ThreeTriosCell(boolean isHole) {
    if (isHole) {
      this.cellState = CellState.HOLE;
    } else {
      this.cellState = CellState.EMPTY;
    }
    this.card = null;
  }

  @Override
  public boolean isHole() {
    return cellState == CellState.HOLE;
  }

  @Override
  public boolean isEmpty() {
    if (isHole()) {
      throw new IllegalStateException("Cell is a hole");
    }
    return cellState == CellState.EMPTY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CellState getCellColor() {
    if (isHole()) {
      throw new IllegalStateException("Cell is a hole");
    } else if (isEmpty()) {
      throw new IllegalStateException("Cell is empty");
    } else {
      switch (card.getCurrentColor()) {
        case RED:
          return CellState.RED;
        case BLUE:
          return CellState.BLUE;
        // should never happen
        default:
          throw new IllegalStateException("Invalid card color");
      }
    }
  }

  @Override
  public CellState getCardState() {
    return null;
  }

  @Override
  public CustomCard getCard() {
    if (isHole()) {
      throw new IllegalStateException("Cell is a hole");
    } else if (isEmpty()) {
      throw new IllegalStateException("Cell is empty");
    }
    return card;
  }

  @Override
  public void playCard(CustomCard card) {
    if (card == null) {
      throw new IllegalArgumentException("Card is null");
    } else if (card.getCurrentColor() == CardColor.UNASSIGNED) {
      throw new IllegalArgumentException("Card is unassigned");
    } else if (cellState == CellState.HOLE) {
      throw new IllegalStateException("Cell is a hole");
    } else if (!isEmpty()) {
      throw new IllegalStateException("Cell has a card");
    }
    this.card = card;
    if (card.getCurrentColor() == CardColor.RED) {
      this.cellState = CellState.RED;
    } else {
      this.cellState = CellState.BLUE;
    }
  }

  @Override
  public void flipCard(CardColor opponentColor) {
    if (card == null) {
      throw new IllegalArgumentException("Card is null");
    } else if (opponentColor == CardColor.UNASSIGNED) {
      throw new IllegalArgumentException("Opponent color is unassigned");
    } else if (this.card.getCurrentColor() == opponentColor) {
      throw new IllegalArgumentException("Cell already has this color");
    } else if (cellState == CellState.HOLE) {
      throw new IllegalStateException("Cell is a hole");
    } else if (cellState == CellState.EMPTY) {
      throw new IllegalStateException("Cell is empty");
    }

    card.setNewColor(opponentColor);
    if (card.getCurrentColor() == CardColor.RED) {
      this.cellState = CellState.RED;
    } else {
      this.cellState = CellState.BLUE;
    }
  }
}
