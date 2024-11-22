package cs3500.threetrios.model;

import java.util.Objects;

/**
 * Represents a cell in the grid of a Three Trios game.
 * Each cell consists of a cell type, a card, and the cell's current owner.
 * Cells can be holes or card cells. Players cannot play cards into holes.
 */
public class Cell {

  private final CellType cellType;
  private Card card;
  private PlayerColor currentOwner;

  /**
   * Constructs a new cell based on the given cell type.
   * The card and owner are not set.
   *
   * @param cellType the cell's cell type
   * @throws NullPointerException if the given cell type is null
   */
  public Cell(CellType cellType) {
    this.cellType = Objects.requireNonNull(cellType);
    this.card = null;
    this.currentOwner = null;
  }

  /**
   * Sets the card of the cell to the given card.
   *
   * @param card the given card
   * @throws IllegalStateException if the cell is a hole
   */
  public void setCard(Card card) {
    if (cellType.equals(CellType.HOLE)) {
      throw new IllegalStateException("Cannot play a card to a hole");
    }
    this.card = card;
  }

  /**
   * Returns the card stored in the cell.
   *
   * @return the card in the cell
   */
  public Card getCard() {
    return card;
  }

  /**
   * Sets the owner of the cell to the given player.
   *
   * @param owner the new owner of the cell
   * @throws IllegalStateException if the cell is a hole or the cell has no card stored
   */
  public void setOwner(PlayerColor owner) {
    if (cellType.equals(CellType.HOLE)) {
      throw new IllegalStateException("Cannot set owner for a hole");
    }
    if (card == null) {
      throw new IllegalStateException("Cannot set owner without a card");
    }
    currentOwner = owner;
  }

  /**
   * Returns the player who owns the cell.
   *
   * @return the player who owns the cell
   */
  public PlayerColor getOwner() {
    return currentOwner;
  }

  /**
   * Returns the cell's type: hole or card cell.
   *
   * @return the cell's type
   */
  public CellType getCellType() {
    return cellType;
  }
}
