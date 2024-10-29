package cs3500.threetrios.controller.readers;

import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Translates a file representing the game grid into a grid of Cells.
 */
public class GridFileReader implements ConfigurationFileReader<Cell[][]> {
  @Override
  public Cell[][] readFile(String filePath) {
    try (FileReader reader = new FileReader(filePath);
         Scanner scanner = new Scanner(reader)) {
      int countCardCells = 0;

      // Read and ensure valid dimensions
      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid grid configuration: missing row count");
      }
      int rows = scanner.nextInt();

      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid grid configuration: missing column count");
      }
      int cols = scanner.nextInt();

      if (rows <= 0 || cols <= 0) {
        throw new IllegalArgumentException("Invalid dimensions: rows and cols must have positive values");
      }

      // Skip to next line
      scanner.nextLine();

      // Read and create grid
      Cell[][] grid = new Cell[rows][cols];
      for (int row = 0; row < rows; row++) {
        // Read and ensure valid row
        if (!scanner.hasNextLine()) {
          throw new IllegalArgumentException("Invalid grid configuration: insufficient rows");
        }

        // Read and ensure valid row length
        String rowAsString = scanner.nextLine();

        if (rowAsString.length() != cols) {
          throw new IllegalArgumentException(
              "Invalid grid configuration: row " + row + " has incorrect length");
        }

        // Read and ensure valid cell types
        for (int col = 0; col < cols; col++) {
          char cell = rowAsString.charAt(col);
          // Ensure valid cell type
          if (cell != 'X' && cell != 'C') {
            throw new IllegalArgumentException(
                "Invalid cell type: " + cell + " at position (" + row + "," + col + ")");
          }
          // if cell is a hole, create a ThreeTriosCell with
          // isHole set to true otherwise, create a ThreeTriosCell
          // with isHole set to false
          boolean isHole = cell == 'X';
          if (!isHole) {
            countCardCells++;
          }
          grid[row][col] = new ThreeTriosCell(isHole);
        }
      }

      if (countCardCells % 2 == 0) {
        throw new IllegalArgumentException(
            "Invalid grid configuration: the number of card cells must be odd");
      }
      // Created grid successfully
      return grid;
    } catch (
        IOException e) {
      throw new IllegalArgumentException("Error reading grid file: " + e.getMessage());
    }
  }
}