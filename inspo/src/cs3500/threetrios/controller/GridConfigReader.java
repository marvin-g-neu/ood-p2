package cs3500.threetrios.controller;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.AbstractConfigReader;
import cs3500.threetrios.model.CellType;

/**
 * Configures the grid of a game of Three Trios.
 */
public class GridConfigReader extends AbstractConfigReader {

  private int rowCount;
  private int colCount;
  private final List<List<CellType>> cellTypes;

  /**
   * Creates a new GridConfigReader.
   */
  public GridConfigReader() {
    cellTypes = new ArrayList<>();
  }

  /**
   * Valid grid configuration files must have the following format:
   * ROWS COLS
   * ROW_0
   * ROW_1
   * ROW_2
   * ...
   * ROWS and COLS must be positive integers representing the number of rows and columns
   * in the grid. The characters in the rows must be either "C" or "X", representing card cells
   * and holes respectively.
   * Lines following the last row are ignored.
   *
   * @param filePath the path of the config file
   * @throws IllegalArgumentException if the file is not found or the file is in the wrong format
   */
  @Override
  public void readConfigFile(String filePath) {
    List<String> lines = super.getConfigFileText(filePath);
    String firstLine = lines.get(0);
    if (firstLine.split(" ").length != 2) {
      throw new IllegalArgumentException("1st line has row and column counts only");
    }
    try {
      rowCount = Integer.parseInt(firstLine.split(" ")[0]);
      colCount = Integer.parseInt(firstLine.split(" ")[1]);
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Non integer row / column count");
    }
    if (rowCount < 1 || colCount < 1) {
      throw new IllegalArgumentException("Row / column count must be a positive integer");
    }
    if (lines.size() < rowCount + 1) {
      throw new IllegalArgumentException("Not enough rows");
    }
    extractCellTypes(lines);
  }

  /**
   * Helper method to add the config file's cell types to cellTypes.
   *
   * @param lines the lines of the config file
   * @throws IllegalArgumentException if config file is in the wrong format
   */
  private void extractCellTypes(List<String> lines) {
    List<List<CellType>> cellTypes = new ArrayList<>();
    for (int rowIdx = 1; rowIdx <= rowCount; rowIdx++) {
      List<CellType> rowCellTypes = new ArrayList<>();
      for (char character : lines.get(rowIdx).toCharArray()) {
        switch (character) {
          case 'X': {
            rowCellTypes.add(CellType.HOLE);
            break;
          }
          case 'C': {
            rowCellTypes.add(CellType.CARDCELL);
            break;
          }
          default:
            throw new IllegalArgumentException("Must be Hole (X) or Card Cell (C)");
        }
      }
      if (rowCellTypes.size() != colCount) {
        throw new IllegalArgumentException("Row is too long");
      }
      cellTypes.add(rowCellTypes);
    }
    this.cellTypes.addAll(cellTypes);
  }

  /**
   * Returns the number of rows in the grid.
   *
   * @return the row count
   * @throws IllegalStateException if a file has not been read yet
   */
  public int getRowCount() {
    if (rowCount == 0) {
      throw new IllegalStateException("File not read yet");
    }
    return rowCount;
  }

  /**
   * Returns the number of columns in the grid.
   *
   * @return the column count
   * @throws IllegalStateException if a file has not been read yet
   */
  public int getColCount() {
    if (rowCount == 0) {
      throw new IllegalStateException("File not read yet");
    }
    return colCount;
  }

  /**
   * Returns the cell types corresponding to each cell in the grid.
   *
   * @return the cell types for each cell
   * @throws IllegalStateException if a file has not been read yet
   */
  public List<List<CellType>> getCellTypes() {
    if (rowCount == 0) {
      throw new IllegalStateException("File not read yet");
    }
    return cellTypes;
  }
}
