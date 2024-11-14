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

import static org.junit.Assert.assertTrue;

public class CornerStrategyTest {
  private ThreeTriosModelInterface mockModel;
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
  public void testCornerStrategyTranscript() {
    strategy.getBestMove(mockModel, PlayerColor.RED);

    String transcript = log.toString();
    assertTrue(transcript.contains("getGameState called"));
    assertTrue(transcript.contains("getGrid called"));
    assertTrue(transcript.contains("getPlayerHand called for color: RED"));

    System.out.println("Strategy execution transcript:");
    System.out.println(transcript);
  }

  @Test
  public void testCornerStrategyChoosesCorner() {
    List<MakePlay> moves = strategy.getBestMove(mockModel, PlayerColor.RED);

    MakePlay move = moves.get(0);
    boolean isCorner = (move.getRow() == 0 && move.getCol() == 0) ||
                      (move.getRow() == 0 && move.getCol() == 2) ||
                      (move.getRow() == 2 && move.getCol() == 0) ||
                      (move.getRow() == 2 && move.getCol() == 2);
    
    assertTrue("Strategy should choose a corner position", isCorner);
    System.out.println("Corner selection transcript:");
    System.out.println(log.toString());
  }
} 