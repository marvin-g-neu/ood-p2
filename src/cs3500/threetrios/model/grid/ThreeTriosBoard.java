package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Grid interface for the Three Trios game.
 */
public class ThreeTriosBoard implements Grid {
  private final Cell[][] board;
  private final int rows;
  private final int cols;

  /**
   * Constructs a ThreeTriosBoard from a 2D array of empty and hole cells.
   *
   * @param board the 2D array of cells
   * @throws IllegalArgumentException if board or cells are null
   * @throws IllegalArgumentException if the board dimensions are jagged
   * @throws IllegalArgumentException if there is an even number of card cells
   */
  public ThreeTriosBoard(Cell[][] board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    if (board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("Board dimensions cannot be zero");
    }

    this.rows = board.length;
    this.cols = board[0].length;
    this.board = new Cell[rows][cols];

    // Copy board and count card cells
    int cardCells = 0;
    for (int i = 0; i < rows; i++) {
      if (board[i].length != cols) {
        throw new IllegalArgumentException("Inconsistent board dimensions");
      }
      for (int j = 0; j < cols; j++) {
        if (board[i][j] == null) {
          throw new IllegalArgumentException("Board cannot contain null cells");
        }
        this.board[i][j] = board[i][j];
        if (!board[i][j].isHole()) {
          cardCells++;
        }
      }
    }

    if (cardCells % 2 == 0) {
      throw new IllegalArgumentException("Even number of card cells not allowed");
    }
  }

  @Override
  public Cell getCell(int row, int col) {
    validatePosition(row, col);
    return board[row][col];
  }

  @Override
  public void placeCard(CustomCard card, int row, int col) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    } else if (card.getCurrentColor() == CardColor.UNASSIGNED) {
      throw new IllegalArgumentException("Card must have a color");
    }
    validatePosition(row, col);
    Cell cell = board[row][col];

    if (cell.isHole()) {
      throw new IllegalStateException("Cannot place card in a hole");
    }
    if (!cell.isEmpty()) {
      throw new IllegalStateException("Cell is already occupied");
    }

    cell.playCard(card);
  }

  @Override
  public int getRows() {
    return rows;
  }

  @Override
  public int getCols() {
    return cols;
  }

  @Override
  public List<Cell> getCardCells() {
    List<Cell> cells = new ArrayList<>();
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        Cell cell = board[r][c];
        if (!cell.isHole()) {
          cells.add(makeCellCopy(cell));
        }
      }
    }
    return cells;
  }

  private Cell makeCellCopy(Cell cell) {
    Cell copy = new ThreeTriosCell(cell.isHole());
    if (!copy.isHole() && !cell.isEmpty()) {
      copy.playCard(cell.getCard());
    }
    return copy;
  }

  @Override
  public int getEmptyCellCount() {
    int count = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Cell cell = board[i][j];
        if (!cell.isHole() && cell.isEmpty()) {
          count++;
        }
      }
    }
    return count;
  }

  @Override
  public Cell[] getAdjacentCells(int row, int col) {
    validatePosition(row, col);
    // in [north, south, east, west] order
    Cell[] adjacent = new ThreeTriosCell[4];

    // Check north
    if (row > 0) {
      adjacent[0] = board[row - 1][col];
    }
    // Check south
    if (row < rows - 1) {
      adjacent[1] = board[row + 1][col];
    }
    // Check east
    if (col < cols - 1) {
      adjacent[2] = board[row][col + 1];
    }
    // Check west
    if (col > 0) {
      adjacent[3] = board[row][col - 1];
    }
    return adjacent;
  }

  /**
   * Validates if the given position is within the board boundaries.
   *
   * @param row the row index
   * @param col the column index
   * @throws IllegalArgumentException if the position is invalid
   */
  private void validatePosition(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid position: (" + row + "," + col + ")");
    }
  }

  @Override
  public Grid copy() {
    Cell[][] copy = new Cell[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        copy[r][c] = board[r][c].copy();
      }
    }
    return new ThreeTriosBoard(copy);
  }
} 
