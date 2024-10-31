package cs3500.threetrios.controller.readers;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class DeckFileReaderTest {
  @Test
  public void readFileThrowsIAEWhenFileCannotBeRead() {
    assertThrows(IllegalArgumentException.class, () -> new GridFileReader().readFile(
        "docs/boards/fakePath.config"));
    assertThrows(IllegalArgumentException.class, () -> new GridFileReader().readFile(
        "docs/cards/AllNecessaryCards.config"));
  }

  @Test
  public void readFileGetsCorrectList() {
    GridFileReader gridFileReader = new GridFileReader();
    Cell[][] grid = gridFileReader.readFile("docs/boards/boardWithNoUnreachableCardCells.config");

    assertEquals(4, grid.length);
    assertEquals(7, grid[0].length);
    Cell firstCell = grid[0][0];
    Cell lastCell = grid[grid.length - 1][grid[0].length - 1];
    assertEquals(CellState.EMPTY, firstCell.getCellState());
    assertEquals(CellState.HOLE, lastCell.getCellState());
  }
}