package cs3500.threetrios.controller;

import cs3500.threetrios.model.cell.Cell;

import java.io.File;
import java.io.IOException;
public class GridFileReader implements ConfigurationFileReader<Cell[][]> {
  @Override
  public Cell[][] readFile(File file) throws IOException {
    return new Cell[0][];
  }
}
