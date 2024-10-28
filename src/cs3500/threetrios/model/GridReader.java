package cs3500.threetrios.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Reads and parses grid configuration files for 
 * the Three Trios game, given in the following format:
 * ROWS COLS
 * ROW_0
 * ROW_1
 * ROW_2
 * ...
 */
public class GridReader {
  /**
   * Reads a grid configuration file and creates a list of cells.
   *
   * @param filePath the path to the grid configuration file
   * @return a 2D array of cells representing the grid
   * @throws IllegalArgumentException if the file format is invalid or file cannot be read
   */
  public static Cell[][] readGrid(String filePath) throws IllegalArgumentException {
    try (FileReader reader = new FileReader(filePath);
         Scanner scanner = new Scanner(reader)) {
      
      // Read and ensure valid dimensions
      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid grid configuration: missing rows");
      }
      int rows = scanner.nextInt();
      
      if (!scanner.hasNextInt()) {
        throw new IllegalArgumentException("Invalid grid configuration: missing columns");
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
          grid[row][col] = new ThreeTriosCell(cell == 'X');
        }
      }
      
      // Created grid successfully
      return grid;
    } catch (IOException e) {
      throw new IllegalArgumentException("Error reading grid file: " + e.getMessage());
    }
  }
}
