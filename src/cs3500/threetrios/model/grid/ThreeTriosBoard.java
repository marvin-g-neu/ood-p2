package cs3500.threetrios.model.grid;

import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.card.ThreeTriosCard;

/**
 * Implementation of the Grid interface for the Three Trios game.
 */
public class ThreeTriosBoard implements Grid {
  private final Cell[][] board;
  private final int rows;
  private final int cols;
  private final int cardCellCount;
  
  /**
   * Constructs a ThreeTriosBoard from a 2D array of cells.
   *
   * @param board the 2D array of cells
   * @throws IllegalArgumentException if board is null, empty, or has inconsistent dimensions
   */
  public ThreeTriosBoard(Cell[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("Invalid board dimensions");
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
    
    this.cardCellCount = cardCells;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Cell getCell(int row, int col) {
    validatePosition(row, col);
    return board[row][col];
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void placeCard(ThreeTriosCard card, int row, int col) {
    validatePosition(row, col);
    Cell cell = board[row][col];
    
    if (cell.isHole()) {
      throw new IllegalArgumentException("Cannot place card in a hole");
    }
    if (!cell.isEmpty()) {
      throw new IllegalArgumentException("Cell is already occupied");
    }

    cell.playCard(card);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getRows() {
    return rows;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getCols() {
    return cols;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getCardCellCount() {
    return cardCellCount;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int getEmptyCellCount() {
    int count = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (board[i][j].isEmpty()) {
          count++;
        }
      }
    }
    return count;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public ThreeTriosCard[] getAdjacentCards(int row, int col) {
    validatePosition(row, col);
    
    // in [north, south, east, west] order
    ThreeTriosCard[] adjacent = new ThreeTriosCard[4];
    
    // Check north
    if (row > 0) {
      adjacent[0] = board[row - 1][col].getCard();
    }
      
    // Check south
    if (row < rows - 1) {
      adjacent[1] = board[row + 1][col].getCard();
    }

    // Check east
    if (col < cols - 1) {
      adjacent[2] = board[row][col + 1].getCard();
    }
      
    // Check west
    if (col > 0) {
      adjacent[3] = board[row][col - 1].getCard();
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
} 
