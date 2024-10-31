package cs3500.threetrios.model;

import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.view.TextualView;
import cs3500.threetrios.view.ThreeTriosTextualView;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClassicalThreeTriosModelTest {
  private ClassicalThreeTriosModel model;
  private Grid grid;
  private List<CustomCard> deck;
  private TextualView view;

  @Before
  public void setUp() {
    // Initialize a simple 3x3 grid with no holes
    Cell[][] cells = new Cell[3][3];
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        cells[r][c] = new ThreeTriosCell(false);
      }
    }
    grid = new ThreeTriosBoard(cells);

    // Initialize a deck with 10 cards
    deck = Arrays.asList(
            new ThreeTriosCard("Red1", AttackValue.THREE, AttackValue.TWO, AttackValue.ONE, AttackValue.ONE, CardColor.RED),
            new ThreeTriosCard("Blue1", AttackValue.TWO, AttackValue.THREE, AttackValue.ONE, AttackValue.ONE, CardColor.BLUE),
            new ThreeTriosCard("Red2", AttackValue.FOUR, AttackValue.ONE, AttackValue.TWO, AttackValue.THREE, CardColor.RED),
            new ThreeTriosCard("Blue2", AttackValue.ONE, AttackValue.FOUR, AttackValue.TWO, AttackValue.THREE, CardColor.BLUE),
            new ThreeTriosCard("Red3", AttackValue.TWO, AttackValue.TWO, AttackValue.THREE, AttackValue.ONE, CardColor.RED),
            new ThreeTriosCard("Blue3", AttackValue.THREE, AttackValue.TWO, AttackValue.ONE, AttackValue.FOUR, CardColor.BLUE),
            new ThreeTriosCard("Red4", AttackValue.ONE, AttackValue.THREE, AttackValue.TWO, AttackValue.FOUR, CardColor.RED),
            new ThreeTriosCard("Blue4", AttackValue.FOUR, AttackValue.ONE, AttackValue.THREE, AttackValue.TWO, CardColor.BLUE),
            new ThreeTriosCard("Red5", AttackValue.THREE, AttackValue.FOUR, AttackValue.ONE, AttackValue.TWO, CardColor.RED),
            new ThreeTriosCard("Blue5", AttackValue.TWO, AttackValue.ONE, AttackValue.FOUR, AttackValue.THREE, CardColor.BLUE)
    );

    model = new ClassicalThreeTriosModel();
    view = new ThreeTriosTextualView(model);
  }

  // Tests for startGame methods

  @Test
  public void testStartGameWithoutShuffle() {
    model.startGame(grid, deck, false);
    assertEquals(GameState.IN_PROGRESS, model.getGameState());
    assertEquals(PlayerColor.RED, model.getCurrentPlayer());

    // Verify hands are correctly initialized
    List<CustomCard> redHand = model.getCurrentPlayerHand();
    assertEquals(5, redHand.size());

    // Switch to BLUE player and verify hand
    model.playTurn(0, 0, 0); // RED plays
    List<CustomCard> blueHand = model.getCurrentPlayerHand();
    assertEquals(5, blueHand.size());
  }

  @Test
  public void testStartGameWithShuffle() {
    model.startGame(grid, deck, true);
    assertEquals(GameState.IN_PROGRESS, model.getGameState());
    assertEquals(PlayerColor.RED, model.getCurrentPlayer());

    // Verify hands are correctly initialized
    List<CustomCard> redHand = model.getCurrentPlayerHand();
    assertEquals(5, redHand.size());

    // Switch to BLUE player and verify hand
    model.playTurn(0, 0, 0); // RED plays
    List<CustomCard> blueHand = model.getCurrentPlayerHand();
    assertEquals(5, blueHand.size());
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameWhenAlreadyStarted() {
    model.startGame(grid, deck);
    model.startGame(grid, deck);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullGrid() {
    model.startGame(null, deck);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullDeck() {
    model.startGame(grid, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullGridAndDeck() {
    model.startGame(null, null, true);
  }

  // Tests for getGrid

  @Test
  public void testGetGridReturnsCorrectGrid() {
    model.startGame(grid, deck);
    assertNotNull(model.getGrid());
    assertEquals(grid.getRows(), model.getGrid().getRows());
    assertEquals(grid.getCols(), model.getGrid().getCols());
  }

  @Test
  public void testGetGridIsImmutable() {
    model.startGame(grid, deck);
    Grid retrievedGrid = model.getGrid();
    try {
      // Attempt to modify the grid
      Cell originalCell = retrievedGrid.getCell(0, 0);
      originalCell.flipCard(CardColor.BLUE);
      fail("Grid should be immutable");
    } catch (UnsupportedOperationException | IllegalStateException e) {
      // Expected exception
    }
  }

  // Tests for getScore

  @Test
  public void testGetScoreInitial() {
    model.startGame(grid, deck);
    assertEquals(0, model.getScore(PlayerColor.RED));
    assertEquals(0, model.getScore(PlayerColor.BLUE));
  }

  @Test
  public void testGetScoreAfterPlays() {
    model.startGame(grid, deck);

    // RED plays at (0,0)
    model.playTurn(0, 0, 0);
    assertEquals(1, model.getScore(PlayerColor.RED));
    assertEquals(0, model.getScore(PlayerColor.BLUE));

    // BLUE plays at (0,1)
    model.playTurn(0, 1, 0);
    assertEquals(1, model.getScore(PlayerColor.RED));
    assertEquals(1, model.getScore(PlayerColor.BLUE));

    // RED plays at (1,1)
    model.playTurn(1, 1, 1);
    assertEquals(2, model.getScore(PlayerColor.RED));
    assertEquals(1, model.getScore(PlayerColor.BLUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetScoreWithNullPlayer() {
    model.startGame(grid, deck);
    model.getScore(null);
  }

  // Tests for getGameState

  @Test
  public void testGetGameStateBeforeStart() {
    assertEquals(GameState.NOT_STARTED, model.getGameState());
  }

  @Test
  public void testGetGameStateAfterStart() {
    model.startGame(grid, deck);
    assertEquals(GameState.IN_PROGRESS, model.getGameState());
  }

  @Test
  public void testGetGameStateAfterGameEnd() {
    model.startGame(grid, deck);

    // Simulate filling the grid
    for (int r = 0; r < grid.getRows(); r++) {
      for (int c = 0; c < grid.getCols(); c++) {
        if (!grid.getCell(r, c).isHole()) {
          model.playTurn(r, c, 0);
        }
      }
    }

    assertTrue(model.getGameState() == GameState.RED_WIN ||
            model.getGameState() == GameState.BLUE_WIN ||
            model.getGameState() == GameState.EARLY_QUIT);
  }

  // Tests for playTurn

  @Test
  public void testPlayTurnValidMove() {
    model.startGame(grid, deck);
    model.playTurn(0, 0, 0);
    assertEquals(PlayerColor.RED, model.getGrid().getCell(0, 0).getCellColor());
    assertEquals(PlayerColor.BLUE, model.getCurrentPlayer());
  }

  @Test
  public void testPlayTurnSwitchesPlayer() {
    model.startGame(grid, deck);
    assertEquals(PlayerColor.RED, model.getCurrentPlayer());
    model.playTurn(0, 0, 0);
    assertEquals(PlayerColor.BLUE, model.getCurrentPlayer());
    model.playTurn(0, 1, 0);
    assertEquals(PlayerColor.RED, model.getCurrentPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayTurnBeforeStart() {
    model.playTurn(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnWithInvalidRow() {
    model.startGame(grid, deck);
    model.playTurn(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnWithInvalidColumn() {
    model.startGame(grid, deck);
    model.playTurn(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnWithInvalidHandIndex() {
    model.startGame(grid, deck);
    model.playTurn(0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayTurnWithOutOfBoundsHandIndex() {
    model.startGame(grid, deck);
    model.playTurn(0, 0, 10);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayTurnOnHoleCell() {
    // Create a grid with a hole at (0,0)
    Cell[][] cellsWithHole = new Cell[3][3];
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        cellsWithHole[r][c] = new ThreeTriosCell(false);
      }
    }
    cellsWithHole[0][0] = new ThreeTriosCell(true);
    Grid holeGrid = new ThreeTriosBoard(cellsWithHole);
    model.startGame(holeGrid, deck);
    model.playTurn(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayTurnOnOccupiedCell() {
    model.startGame(grid, deck);
    model.playTurn(0, 0, 0); // First move to (0,0)
    model.playTurn(0, 0, 1); // Attempt to play again on the same cell
  }

  @Test
  public void testPlayTurnBattleOutcome() {
    model.startGame(grid, deck);

    // RED plays a strong card at (0,0)
    model.playTurn(0, 0, 0);
    assertEquals(PlayerColor.RED, model.getGrid().getCell(0, 0).getCellColor());

    // BLUE plays a weaker card adjacent to RED's card at (0,1)
    model.playTurn(0, 1, 1);
    assertEquals(PlayerColor.BLUE, model.getGrid().getCell(0, 1).getCellColor());

    // RED plays another card adjacent to BLUE's card, triggering a battle
    model.playTurn(1, 1, 1);
    assertEquals(PlayerColor.RED, model.getGrid().getCell(1, 1).getCellColor());

    // At this point, verify the battle logic by checking adjacent cells
    // This is a simplistic check; more detailed assertions can be added based on battle rules
    assertEquals(PlayerColor.RED, model.getGrid().getCell(1, 1).getCellColor());
  }

  // Additional Tests for Comprehensive Coverage

  @Test
  public void testPlayTurnEndsGameWhenGridFull() {
    model.startGame(grid, deck);
    // Fill the grid with RED cards
    for (int r = 0; r < grid.getRows(); r++) {
      for (int c = 0; c < grid.getCols(); c++) {
        if (!grid.getCell(r, c).isHole()) {
          model.playTurn(r, c, 0);
        }
      }
    }
    assertTrue(model.getGameState() == GameState.RED_WIN ||
            model.getGameState() == GameState.BLUE_WIN ||
            model.getGameState() == GameState.EARLY_QUIT);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayTurnAfterGameEnded() {
    model.startGame(grid, deck);
    // End the game by filling the grid
    for (int r = 0; r < grid.getRows(); r++) {
      for (int c = 0; c < grid.getCols(); c++) {
        if (!grid.getCell(r, c).isHole()) {
          model.playTurn(r, c, 0);
        }
      }
    }
    // Attempt to play another turn after game has ended
    model.playTurn(0, 0, 0);
  }
}