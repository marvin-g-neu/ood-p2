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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CornerStrategyTest {
  private CornerStrategy strategy;
  private ThreeTriosModelInterface model;
  private ThreeTriosModelInterface mockModel;
  private Grid grid;
  private List<CustomCard> deck;
  private StringBuilder log;

  @Before
  public void setup() {
    log = new StringBuilder();
    grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/3x3boardWithNoHolesForMockTesting.config"));
    deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    mockModel = new MockModel(log, deck, grid);
    model = new ClassicalThreeTriosModel();
    strategy = new CornerStrategy();
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
    Grid g = new ThreeTriosBoard(new Cell[][]{{empty, empty}});
    m.startGame(g, deck, false);

    g.placeCard(m.getCurrentPlayerHand().get(0), 0, 0);
    assertEquals(0, m.getPlayerHand(PlayerColor.RED).size());
    assertThrows(IllegalStateException.class, () -> strategy.getBestMove(m, PlayerColor.RED));
  }

  @Test
  public void getBestMoveChoosesCornerFirst() {
    // The strategy should choose a corner position (0,0) for the first move
    List<MakePlay> bestMove = strategy.getBestMove(model, PlayerColor.RED);
    assertEquals(1, bestMove.size());
    MakePlay move = bestMove.get(0);
    // Should be one of the corners (0,0), (0,2), (2,0), or (2,2)
    boolean isCorner = (move.row == 0 && move.col == 0) ||
                      (move.row == 0 && move.col == 2) ||
                      (move.row == 2 && move.col == 0) ||
                      (move.row == 2 && move.col == 2);
    assertTrue(isCorner);
  }

  @Test
  public void getBestMoveFallsBackToUpperLeftWhenNoCornersAvailable() {
    // Fill all corners
    model.playTurn(0, 0, 0);  // top-left
    model.playTurn(0, 0, 2);  // top-right
    model.playTurn(0, 2, 0);  // bottom-left
    model.playTurn(0, 2, 2);  // bottom-right

    List<MakePlay> bestMove = strategy.getBestMove(model, PlayerColor.RED);
    assertEquals(1, bestMove.size());
    MakePlay move = bestMove.get(0);
    // Should choose the uppermost-leftmost available position
    assertEquals(0, move.row);
    assertEquals(1, move.col);
  }

  @Test
  public void getBestMoveChoosesLeastVulnerableCorner() {
    // Play a card that makes one corner more vulnerable than others
    model.playTurn(0, 1, 0);  // Play adjacent to top-left corner

    List<MakePlay> bestMove = strategy.getBestMove(model, PlayerColor.BLUE);
    assertEquals(1, bestMove.size());
    MakePlay move = bestMove.get(0);
    // Should not choose the top-left corner as it's more vulnerable
    assertFalse(move.row == 0 && move.col == 0);
  }

  @Test
  public void testCornerStrategyTranscript() {
    // Test the strategy's behavior
    strategy.getBestMove(mockModel, PlayerColor.RED);
    
    // Verify the expected sequence of method calls
    String transcript = log.toString();
    assertTrue(transcript.contains("getGameState called"));
    assertTrue(transcript.contains("getGrid called"));
    assertTrue(transcript.contains("getPlayerHand called for color: RED"));
    
    // Print the transcript for debugging
    System.out.println("Strategy execution transcript:");
    System.out.println(transcript);
  }

  @Test
  public void testCornerStrategyChoosesCorner() {
    List<MakePlay> moves = strategy.getBestMove(mockModel, PlayerColor.RED);
    
    // Verify that the chosen move is a corner position
    MakePlay move = moves.get(0);
    boolean isCorner = (move.row == 0 && move.col == 0) ||
                      (move.row == 0 && move.col == 2) ||
                      (move.row == 2 && move.col == 0) ||
                      (move.row == 2 && move.col == 2);
    
    assertTrue("Strategy should choose a corner position", isCorner);
    
    // Print the transcript for debugging
    System.out.println("Corner selection transcript:");
    System.out.println(log.toString());
  }
} 