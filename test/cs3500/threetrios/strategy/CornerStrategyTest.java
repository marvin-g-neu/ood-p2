package cs3500.threetrios.strategy;

import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test for the CornerStrategy strategy class.
 */
public class CornerStrategyTest {
  private CornerStrategy strategy;
  private ThreeTriosModelInterface model;
  private ThreeTriosModelInterface mockModel;
  private List<CustomCard> deck;
  private StringBuilder log;

  @Before
  public void setup() {
    log = new StringBuilder();
    Grid grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/3x3boardWithNoHolesForMockTesting.config"));
    deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    mockModel = new MockModel(log, deck, grid);
    model = new ClassicalThreeTriosModel();
    strategy = new CornerStrategy();
    model.startGame(grid, deck);
  }

  @Test
  public void getBestMoveThrowsForNullArgs() {
    assertThrows(IllegalArgumentException.class,
        () -> strategy.getBestMove(null, PlayerColor.RED));
    assertThrows(IllegalArgumentException.class,
        () -> strategy.getBestMove(model, null));
  }

  @Test
  public void getBestMoveThrowsGameNotInProgress() {
    assertThrows(IllegalStateException.class,
        () -> strategy.getBestMove(new ClassicalThreeTriosModel(), PlayerColor.RED));

    model.endGame();
    assertThrows(IllegalStateException.class,
        () -> strategy.getBestMove(model, PlayerColor.RED));
  }

  @Test
  public void getBestMoveThrowsWhenNoMove() {
    Cell empty = new ThreeTriosCell(false);
    ThreeTriosModelInterface m = new ClassicalThreeTriosModel();
    Grid g = new ThreeTriosBoard(new Cell[][]{{empty, empty.copy(), empty.copy()}});
    m.startGame(g, deck, false);

    m.playTurn(0, 0, 0); // Assuming handIndex = 0
    m.playTurn(0, 0, 2); // Assuming handIndex = 2
    m.playTurn(0, 2, 0); // Assuming handIndex = 0
    m.playTurn(0, 2, 2); // Assuming handIndex = 2

    assertEquals(0, m.getPlayerHand(PlayerColor.RED).size());
    assertThrows(IllegalStateException.class, () -> strategy.getBestMove(m, PlayerColor.RED));
  }

  @Test
  public void getBestMoveChoosesCornerFirst() {
    // The strategy should choose a corner position (0,0) for the first move
    MakePlay bestMove = strategy.getBestMove(model, PlayerColor.RED);
    // Should be one of the corners (0,0), (0,2), (2,0), or (2,2)
    boolean isCorner = (bestMove.getRow() == 0 && bestMove.getCol() == 0) ||
        (bestMove.getRow() == 0 && bestMove.getCol() == 2) ||
        (bestMove.getRow() == 2 && bestMove.getCol() == 0) ||
        (bestMove.getRow() == 2 && bestMove.getCol() == 2);
    assertTrue(isCorner);
  }

  @Test
  public void getBestMoveFallsBackToUpperLeftWhenNoCornersAvailable() {
    model.playTurn(0, 0, 0);  // top-left
    model.playTurn(0, 2, 2);  // top-right
    model.playTurn(2, 0, 0);  // bottom-left
    model.playTurn(2, 2, 2);  // bottom-right

    MakePlay bestMove = strategy.getBestMove(model, PlayerColor.RED);

    assertEquals(0, bestMove.getRow());
    assertEquals(1, bestMove.getCol());
  }

  @Test
  public void getBestMoveChoosesLeastVulnerableCorner() {
    model.playTurn(1, 0, 0);

    MakePlay bestMove = strategy.getBestMove(model, PlayerColor.BLUE);
    assertFalse(bestMove.getRow() == 0 && bestMove.getCol() == 0);
  }

  @Test
  public void testCornerStrategyTranscript() {
    strategy.getBestMove(mockModel, PlayerColor.RED);

    String transcript = log.toString();
    assertTrue(transcript.contains("getGameState called"));
    assertTrue(transcript.contains("getGrid called"));
    assertTrue(transcript.contains("getPlayerHand called"));

    System.out.println("Strategy execution transcript:");
    System.out.println(transcript);
  }

  @Test
  public void testCornerStrategyChoosesCorner() {
    MakePlay move = strategy.getBestMove(mockModel, PlayerColor.RED);

    boolean isCorner = (move.getRow() == 0 && move.getCol() == 0) ||
        (move.getRow() == 0 && move.getCol() == 2) ||
        (move.getRow() == 2 && move.getCol() == 0) ||
        (move.getRow() == 2 && move.getCol() == 2);

    assertTrue("Strategy should choose a corner position", isCorner);

    // Print the transcript for debugging
    System.out.println("Corner selection transcript:");
    System.out.println(log.toString());
  }
} 