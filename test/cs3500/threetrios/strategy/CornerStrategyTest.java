package cs3500.threetrios.strategy;

import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CornerStrategyTest {
  private MockModel mockModel;
  private Strategy strategy;
  private StringBuilder log;

  @Before
  public void setup() {
    log = new StringBuilder();
    Grid grid = new ThreeTriosBoard(
            new GridFileReader().readFile(
                    "docs/boards/3x3boardWithNoHolesForMockTesting.config"));
    List<CustomCard> deck = new DeckFileReader().readFile("docs/cards/someCards.config");

    mockModel = new MockModel(log, deck, grid);
    strategy = new CornerStrategy();
  }

  @Test
  public void testCornerStrategyEmptyBoard() {
    strategy.getBestMove(mockModel, PlayerColor.RED);
    
    String expected = String.join("\n",
        "Checking game state: IN_PROGRESS",
        "Getting current player hand",
        "Checking corner cell: (0, 0)",
        "Calculating vulnerability for corner (0, 0)",
        "Checking corner cell: (0, 2)",
        "Calculating vulnerability for corner (0, 2)",
        "Checking corner cell: (2, 0)",
        "Calculating vulnerability for corner (2, 0)",
        "Checking corner cell: (2, 2)",
        "Calculating vulnerability for corner (2, 2)",
        "Comparing corner vulnerabilities",
        "Selected least vulnerable corner: (0, 0)",
        "");

    assertEquals(expected, log.toString());
  }

  @Test
  public void testCornerStrategyFilledCorners() {
    // Setup board with filled corners
    Grid grid = mockModel.getGrid();
    // Fill corners logic here...
    
    strategy.getBestMove(mockModel, PlayerColor.RED);
    
    String expected = String.join("\n",
        "Checking game state: IN_PROGRESS",
        "Getting current player hand",
        "Checking corner cell: (0, 0) - occupied",
        "Checking corner cell: (0, 2) - occupied",
        "Checking corner cell: (2, 0) - occupied",
        "Checking corner cell: (2, 2) - occupied",
        "Finding uppermost-leftmost empty cell",
        "Checking cell: (0, 1)",
        "Selected uppermost-leftmost position: (0, 1)",
        "");

    assertEquals(expected, log.toString());
  }
} 