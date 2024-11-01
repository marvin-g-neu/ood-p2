package cs3500.threetrios.model.grid;

import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * For testing functionality of Grid interface methods across implementations.
 */
public class GridTest {
  private Grid ttbNoHolesGrid;
  private Grid ttbNoUnreachableGrid;
  private Grid ttbOneByOneGrid;
  private CustomCard normalCard;
  private CustomCard unassignedCard;
  private Cell[][] noUnreachableBoard;

  @Before
  public void setUp() {
    GridFileReader gridReader = new GridFileReader();
    Cell[][] noHolesBoard = gridReader.readFile("docs/boards/boardWithNoHoles.config");
    noUnreachableBoard = gridReader.readFile(
        "docs/boards/boardWithNoUnreachableCardCells.config");
    Cell[][] oneByOneBoard = gridReader.readFile("docs/boards/oneByOneBoard.config");
    ttbNoHolesGrid = new ThreeTriosBoard(noHolesBoard);
    ttbNoUnreachableGrid = new ThreeTriosBoard(noUnreachableBoard);
    ttbOneByOneGrid = new ThreeTriosBoard(oneByOneBoard);

    normalCard = new ThreeTriosCard("Card", AttackValue.A,
        AttackValue.A, AttackValue.A, AttackValue.A, CardColor.RED);
    unassignedCard = new ThreeTriosCard("Card", AttackValue.A,
        AttackValue.A, AttackValue.A, AttackValue.A);
  }

  @Test
  public void getCellThrowsForOutOfRange() {
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getCell(5, 1));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getCell(4, 7));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getCell(-1, 3));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getCell(2, -1));
  }

  @Test
  public void getCellReturnsCell() {
    assertEquals(CellState.EMPTY, ttbNoUnreachableGrid.getCell(1, 2).getCellState());
    assertEquals(CellState.HOLE, ttbNoUnreachableGrid.getCell(1, 3).getCellState());
    ttbNoUnreachableGrid.placeCard(normalCard, 1, 2);
    assertEquals(CellState.RED, ttbNoUnreachableGrid.getCell(1, 2).getCellState());
    assertEquals(CellState.EMPTY, ttbNoUnreachableGrid.getCell(0, 0).getCellState());
  }

  @Test
  public void getRowsReturnsRows() {
    assertEquals(5, ttbNoHolesGrid.getRows());
    assertEquals(4, ttbNoUnreachableGrid.getRows());
  }

  @Test
  public void getColumnsReturnsColumns() {
    assertEquals(7, ttbNoHolesGrid.getCols());
    assertEquals(7, ttbNoUnreachableGrid.getCols());
  }

  @Test
  public void getCardCellsReturnsCardCells() {
    assertEquals(35, ttbNoHolesGrid.getCardCells().size());
    assertEquals(19, ttbNoUnreachableGrid.getCardCells().size());
    ttbNoUnreachableGrid.placeCard(normalCard, 1, 2);
    assertEquals(19, ttbNoUnreachableGrid.getCardCells().size());
    for (Cell cell : ttbNoUnreachableGrid.getCardCells()) {
      assertNotSame(CellState.HOLE, cell.getCellState());
    }
  }

  @Test
  public void getCardCellsReturnsCopies() {
    assertEquals(CellState.EMPTY, noUnreachableBoard[2][0].getCellState());
    assertFalse(ttbNoUnreachableGrid.getCardCells().contains(noUnreachableBoard[2][0]));
  }

  @Test
  public void getEmptyCellCountReturnsCount() {
    assertEquals(35, ttbNoHolesGrid.getEmptyCellCount());
    assertEquals(19, ttbNoUnreachableGrid.getEmptyCellCount());
    ttbNoUnreachableGrid.placeCard(normalCard, 1, 2);
    assertEquals(18, ttbNoUnreachableGrid.getEmptyCellCount());
  }

  @Test
  public void getAdjacentCellsThrowsForOutOfRange() {
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getAdjacentCells(5, 1));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getAdjacentCells(4, 7));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getAdjacentCells(-1, 3));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid.getAdjacentCells(2, -1));
  }

  @Test
  public void getAdjacentCellsGetsAdjacentCells() {
    Cell[] noUnreachableTopLeft = ttbNoUnreachableGrid.getAdjacentCells(0, 0);
    Cell[] noUnreachableBottomRight = ttbNoUnreachableGrid.getAdjacentCells(3, 6);
    Cell[] oneByOneAdjacent = ttbOneByOneGrid.getAdjacentCells(0, 0);
    Cell[] noUnreachableMiddle = ttbNoUnreachableGrid.getAdjacentCells(2, 4);

    // North
    assertNull(noUnreachableTopLeft[0]);
    assertNull(oneByOneAdjacent[0]);
    assertEquals(noUnreachableBoard[2][6], noUnreachableBottomRight[0]);
    assertEquals(noUnreachableBoard[1][4], noUnreachableMiddle[0]);

    // South
    assertEquals(noUnreachableBoard[1][0], noUnreachableTopLeft[1]);
    assertNull(oneByOneAdjacent[1]);
    assertNull(noUnreachableBottomRight[1]);
    assertEquals(noUnreachableBoard[3][4], noUnreachableMiddle[1]);

    // East
    assertEquals(noUnreachableBoard[0][1], noUnreachableTopLeft[2]);
    assertNull(oneByOneAdjacent[2]);
    assertNull(noUnreachableBottomRight[2]);
    assertEquals(noUnreachableBoard[2][5], noUnreachableMiddle[2]);

    // West
    assertNull(noUnreachableTopLeft[3]);
    assertNull(oneByOneAdjacent[3]);
    assertEquals(noUnreachableBoard[3][5], noUnreachableBottomRight[3]);
    assertEquals(noUnreachableBoard[2][3], noUnreachableMiddle[3]);
  }

  @Test
  public void placeCardThrowsWhenExpected() {
    // null card
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(null, 1, 2));
    // dimensions out of bounds
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(normalCard, 5, 1));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(normalCard, 4, 7));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(normalCard, -1, 3));
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(normalCard, 2, -1));
    // unassigned color
    assertThrows(IllegalArgumentException.class, () -> ttbNoHolesGrid
        .placeCard(unassignedCard, 2, 1));
    // cell is hole or not empty
    assertThrows(IllegalStateException.class, () -> ttbNoUnreachableGrid
        .placeCard(normalCard, 0, 1));
    unassignedCard.setNewColor(CardColor.BLUE);
    ttbNoUnreachableGrid.placeCard(unassignedCard, 0, 0);
    assertThrows(IllegalStateException.class, () -> ttbNoUnreachableGrid
        .placeCard(normalCard, 0, 0));
  }

  @Test
  public void placeCardPlacesAtCorrectPosition() {
    ttbNoHolesGrid.placeCard(normalCard, 1, 2);
    assertEquals(normalCard, ttbNoHolesGrid.getCell(1, 2).getCard());
    assertTrue(ttbNoHolesGrid.getCell(0, 2).isEmpty());
  }
}