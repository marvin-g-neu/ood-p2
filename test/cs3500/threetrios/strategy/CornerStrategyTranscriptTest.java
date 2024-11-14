package cs3500.threetrios.strategy;

import cs3500.threetrios.controller.readers.DeckFileReader;
import cs3500.threetrios.controller.readers.GridFileReader;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CornerStrategyTranscriptTest {

  @Test
  public void testCornerStrategyTranscript() {
    StringBuilder log = new StringBuilder();
    Grid grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/3x3board.config"));
    List<CustomCard> deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    
    ThreeTriosModelInterface mockModel = new MockModel(log, deck, grid);
    Strategy strategy = new CornerStrategy();
    
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
    StringBuilder log = new StringBuilder();
    Grid grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/3x3board.config"));
    List<CustomCard> deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    
    ThreeTriosModelInterface mockModel = new MockModel(log, deck, grid);
    Strategy strategy = new CornerStrategy();
    
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