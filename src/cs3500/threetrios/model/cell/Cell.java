package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.PlayerColor;

/**
 * Represents a cell in the Three Trios game, where
 * a cell can either be a hole, empty, or contain a
 * card with a specific color. Note that a cell can
 * only contain a card with a specific color if the
 * cell is not a hole.
 */
public interface Cell {
  /**
   * Checks if a cell is a hole.
   *
   * @return true if the cell is a hole, false otherwise
   */
  boolean isHole();

  /**
   * Checks if a cell is empty.
   *
   * @return true if the cell is empty, false otherwise
   * @throws IllegalStateException if the cell is a hole
   */
  boolean isEmpty();

  /**
   * Gets the color of the card in a cell.
   *
   * @return the color of the card in the cell
   * @throws IllegalStateException if the cell is a hole or empty
   */
  PlayerColor getCellColor();

  /**
   * Gets the state of the cell.
   *
   * @return the cell state
   */
  CellState getCellState();

  /**
   * Gets the card in a cell.
   *
   * @return the card in the cell
   * @throws IllegalStateException if the cell is a hole or empty
   */
  CustomCard getCard();

  /**
   * Sets the card in a cell.
   *
   * @param card the card to set in the cell
   * @throws IllegalArgumentException if card is null
   * @throws IllegalArgumentException if card color is UNASSIGNED
   * @throws IllegalStateException    if the cell is a hole
   * @throws IllegalStateException    if the cell has a card
   */
  void playCard(CustomCard card);

  /**
   * Flips the color of the card in a cell to the opponent's color.
   *
   * @param opponentColor the color of the opponent
   * @throws IllegalArgumentException if opponentColor is null
   * @throws IllegalArgumentException if opponentColor is UNASSIGNED
   * @throws IllegalArgumentException if color wouldn't change
   * @throws IllegalStateException    if the cell is a hole or is empty
   */
  void flipCard(CardColor opponentColor);

  /**
   * Make a copy of this cell.
   * @return a copy of this cell
   */
  Cell copy();
}
