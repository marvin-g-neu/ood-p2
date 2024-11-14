package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;

import java.util.List;

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
   * @throws IllegalArgumentException if the row or column is out of range
   */
  Cell getCell(int row, int col);

  /**
   * Places a card at the specified position.
   *
   * @param card the card to place
   * @param row  the row index
   * @param col  the column index
   * @throws IllegalArgumentException if the card is null
   * @throws IllegalArgumentException if the card color is UNASSIGNED
   * @throws IllegalArgumentException if the row or column is out of range
   * @throws IllegalStateException    if the cell is not empty or is a hole
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
   * Gets all card cells (non-hole cells) in the grid and returns copies of them.
   *
   * @return copies of all card cells
   */
  List<Cell> getCardCells();

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
   * @throws IllegalArgumentException if the row or column is out of range
   */
  Cell[] getAdjacentCells(int row, int col);

  /**
   * Creates a deep copy of the current grid state.
   *
   * @return a new Grid instance with the same state
   */
  Grid copy();
}
