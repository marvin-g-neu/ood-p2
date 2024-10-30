package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.PlayerColor;
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

  @Override
  public PlayerColor getCellColor() {
    if (isHole()) {
      throw new IllegalStateException("Cell is a hole");
    } else if (isEmpty()) {
      throw new IllegalStateException("Cell is empty");
    } else {
      switch (card.getCurrentColor()) {
        case BLUE:
          return PlayerColor.BLUE;
        case RED:
          return PlayerColor.RED;
        default: // should never happen
          throw new IllegalStateException("Unknown color");
      }
    }
  }

  @Override
  public CellState getCellState() {
    return cellState;
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
    if (opponentColor == null) {
      throw new IllegalArgumentException("Opponent color is null");
    } else if (cellState == CellState.HOLE) {
      throw new IllegalStateException("Cell is a hole");
    } else if (cellState == CellState.EMPTY) {
      throw new IllegalStateException("Cell is empty");
    } else if (opponentColor == CardColor.UNASSIGNED) {
      throw new IllegalArgumentException("Opponent color is unassigned");
    } else if (this.card.getCurrentColor() == opponentColor) {
      throw new IllegalArgumentException("Cell already has this color");
    }

    card.setNewColor(opponentColor);
    if (card.getCurrentColor() == CardColor.RED) {
      this.cellState = CellState.RED;
    } else {
      this.cellState = CellState.BLUE;
    }
  }
}
