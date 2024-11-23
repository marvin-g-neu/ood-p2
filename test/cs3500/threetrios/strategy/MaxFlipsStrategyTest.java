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

import static org.junit.Assert.*;

public class MaxFlipsStrategyTest {
  Strategy strategy;
  ThreeTriosModelInterface model;
  Grid grid;
  List<CustomCard> deck;
  StringBuilder log;
  ThreeTriosModelInterface mockModel;

  @Before
  public void setup() {
    strategy = new MaxFlipsStrategy();
    grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/boardWithNoUnreachableCardCells.config"));
    deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    model = new ClassicalThreeTriosModel();
    model.startGame(grid, deck, false);

    // Initialize MockModel if needed for certain tests
    log = new StringBuilder();
    mockModel = new MockModel(log, new ArrayList<>(deck), grid);
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

    assertEquals(2, m.getPlayerHand(PlayerColor.RED).size());
    m.playTurn(0, 0, 0); // Play first card at (0,0)
    m.playTurn(0, 1, 0); // Play second card at (0,1)
    assertEquals(0, m.getPlayerHand(PlayerColor.RED).size());
    assertThrows(IllegalStateException.class, () -> strategy.getBestMove(m, PlayerColor.RED));
  }

  @Test
  public void getBestMoveFindsMaxFlips() {
    model.playTurn(0, 0, 0);
    MakePlay expected = new MakePlay(1, 0, 1);
    MakePlay best = strategy.getBestMove(model, PlayerColor.BLUE);
    System.out.println(expected.getCardInHand() + ", " + expected.getRow() + ", " + expected.getCol());
    System.out.println(best.getCardInHand() + ", " + best.getRow() + ", " + best.getCol());
    assertEquals(expected, best);
  }

  @Test
  public void getBestMoveFindsMaxFlipsWhenChain() {
    model.playTurn(0, 0, 0);
    model.playTurn(1, 0, 0);

    List<MakePlay> bestPlays = new ArrayList<>();
    bestPlays.add(new MakePlay(1, 1, 1));
    bestPlays.add(new MakePlay(2, 1, 1));
    bestPlays.add(new MakePlay(3, 1, 1));
    bestPlays.add(new MakePlay(4, 1, 1));
    bestPlays.add(new MakePlay(5, 1, 1));
    assertTrue(bestPlays.contains(strategy.getBestMove(model, PlayerColor.RED)));
    // Assuming MakePlay has proper equals method
    model.playTurn(1, 1, 4);
  }

  @Test
  public void getBestMoveWorksForLoneOption() {
    // Implement the test logic as needed
    // Example:
    ThreeTriosModelInterface singleMoveModel = new ClassicalThreeTriosModel();
    Grid singleMoveGrid = new ThreeTriosBoard(new Cell[][]{
        {new ThreeTriosCell(true), new ThreeTriosCell(false)},
        {new ThreeTriosCell(false), new ThreeTriosCell(false)}
    });
    singleMoveModel.startGame(singleMoveGrid, deck, false);
    singleMoveModel.playTurn(0, 0, 0); // Play the lone available move

    MakePlay bestMove = strategy.getBestMove(singleMoveModel, PlayerColor.GREEN);
    assertEquals(0, bestMove.getRow());
    assertEquals(0, bestMove.getCol());
  }
}