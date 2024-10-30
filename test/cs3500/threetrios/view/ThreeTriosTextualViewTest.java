package cs3500.threetrios.view;

import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ThreeTriosTextualViewTest {
  private ClassicalThreeTriosModel model;
  private ThreeTriosTextualView view;
  private Grid grid;
  private List<CustomCard> deck;

  @Before
  public void setUp() {
    // Initialize grid with a simple 3x3 board with no holes
    CellState[][] boardState = {
            {CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
            {CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
            {CellState.EMPTY, CellState.EMPTY, CellState.EMPTY}
    };
    Cell[][] cells = new Cell[3][3];
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        cells[r][c] = new ThreeTriosCell(false);
      }
    }
    
    // Initialize grid
    grid = new ThreeTriosBoard(cells);
    assertNotNull("Grid should not be null", grid);

    // Initialize deck with 10 cards
    deck = Arrays.asList(
            new ThreeTriosCard("Red1", AttackValue.THREE, AttackValue.TWO,
                    AttackValue.ONE, AttackValue.ONE, CardColor.RED),
            new ThreeTriosCard("Blue1", AttackValue.TWO, AttackValue.THREE,
                    AttackValue.ONE, AttackValue.ONE, CardColor.BLUE),
            new ThreeTriosCard("Red2", AttackValue.FOUR, AttackValue.ONE,
                    AttackValue.TWO, AttackValue.THREE, CardColor.RED),
            new ThreeTriosCard("Blue2", AttackValue.ONE, AttackValue.FOUR,
                    AttackValue.TWO, AttackValue.THREE, CardColor.BLUE),
            new ThreeTriosCard("Red3", AttackValue.TWO, AttackValue.TWO,
                    AttackValue.THREE, AttackValue.ONE, CardColor.RED),
            new ThreeTriosCard("Blue3", AttackValue.THREE, AttackValue.TWO,
                    AttackValue.ONE, AttackValue.FOUR, CardColor.BLUE),
            new ThreeTriosCard("Red4", AttackValue.ONE, AttackValue.THREE,
                    AttackValue.TWO, AttackValue.FOUR, CardColor.RED),
            new ThreeTriosCard("Blue4", AttackValue.FOUR, AttackValue.ONE,
                    AttackValue.THREE, AttackValue.TWO, CardColor.BLUE),
            new ThreeTriosCard("Red5", AttackValue.THREE, AttackValue.FOUR,
                    AttackValue.ONE, AttackValue.TWO, CardColor.RED),
            new ThreeTriosCard("Blue5", AttackValue.TWO, AttackValue.ONE,
                    AttackValue.FOUR, AttackValue.THREE, CardColor.BLUE)
    );
    assertNotNull("Deck should not be null", deck);
    assertEquals("Deck should contain 10 cards", 10, deck.size());

    // Initialize model
    model = new ClassicalThreeTriosModel(true);
    assertNotNull("Model should not be null", model);

    // Start the game
    model.startGame(grid, deck);

    // Initialize view
    view = new ThreeTriosTextualView(model);
    assertNotNull("View should not be null", view);
  }

  @Test
  public void testRenderInitialState() {
    String expected = "PlayerColor: RED\n" +
            "___\n" +
            "___\n" +
            "___\n" +
            "Hand:\n" +
            "Red1\n" +
            "Red2\n";

    assertEquals(expected, view.render());
  }

  @Test
  public void testRenderAfterMoves() {
    // Red plays Red1 at (0,0)
    model.playTurn(0, 0, 0); // Assuming hand index 0 is Red1
    model.endTurn();

    // Blue plays Blue1 at (0,1)
    model.playTurn(0, 1, 0); // Assuming hand index 0 is Blue1
    model.endTurn();

    // Red plays Red2 at (1,1)
    model.playTurn(1, 1, 0); // Assuming hand index 0 is Red2
    model.endTurn();

    // Blue plays Blue2 at (2,2)
    model.playTurn(2, 2, 0); // Assuming hand index 0 is Blue2
    model.endTurn();

    String expected = "PlayerColor: RED\n" +
            "R B _\n" +
            "_ R _\n" +
            "_ _ B\n" +
            "Hand:\n" +
            "Red2\n" +
            "Blue2\n";

    assertEquals(expected, view.render());
  }

  @Test
  public void testRenderAfterInvalidMove() {
    // Attempt to play Red1 at a hole (if any), but since no holes, simulate by trying to play out of bounds
    try {
      model.playTurn(3, 3, 0); // Invalid position
    } catch (IllegalArgumentException e) {
      // Expected exception
    }

    // Render should remain the same as initial
    String expected = "PlayerColor: RED\n" +
            "___\n" +
            "___\n" +
            "___\n" +
            "Hand:\n" +
            "Red1\n" +
            "Red2\n";

    assertEquals(expected, view.render());
  }

  @Test
  public void testRenderAfterFlipCard() {
    // Red plays Red1 at (0,0)
    model.playTurn(0, 0, 0); // Red1
    model.endTurn();

    // Blue plays Blue1 at (0,1)
    model.playTurn(0, 1, 0); // Blue1
    model.endTurn();

    // Red plays Red2 at (1,1)
    model.playTurn(1, 1, 0); // Red2
    model.endTurn();

    // Blue plays Blue2 at (0,0) to battle Red1
    model.playTurn(0, 0, 0); // Blue2 attempts to place on (0,0)
    model.endTurn();

    String expected = "PlayerColor: RED\n" +
            "B B _\n" +
            "_ R _\n" +
            "_ _ _\n" +
            "Hand:\n" +
            "Red2\n" +
            "Blue2\n";

    assertEquals(expected, view.render());
  }
}