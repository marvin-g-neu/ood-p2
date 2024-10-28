package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.card.ThreeTriosCard;
/**
 * Represents a game board grid for Three Trios.
 * The grid consists of cells that can either be holes or spaces for cards.
 */
public interface Grid {
  /**
   * Gets the cell at the specified position.
   *
   * @param row the row index
   * @param col the column index
   * @return the cell at the specified position
   * @throws IllegalArgumentException if the position is invalid
   */
  Cell getCell(int row, int col);

  /**
   * Places a card at the specified position.
   *
   * @param card the card to place
   * @param row the row index
   * @param col the column index
   * @throws IllegalArgumentException if the position is 
   * invalid or cell cannot accept card
   */
  void placeCard(CustomCard card, int row, int col);

  /**
   * Gets the number of rows in the grid.
   *
   * @return the number of rows
   */
  int getRows();

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns
   */
  int getCols();

  /**
   * Counts the number of card cells (non-hole cells) in the grid.
   *
   * @return the number of card cells
   */
  int getCardCellCount();

  /**
   * Gets the number of empty card cells in the grid.
   *
   * @return the number of empty card cells
   */
  int getEmptyCellCount();

  /**
   * Gets the cards in the cells adjacent to the specified position.
   *
   * @param row the row index
   * @param col the column index
   * @return array of adjacent cells in order [north, south, east, west], null if no adjacent cell
   * @throws IllegalArgumentException if the position is invalid
   */
  CustomCard[] getAdjacentCards(int row, int col);
}
