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

public class MaxFlipsStrategyTest {
  Strategy strategy;
  ThreeTriosModelInterface model;
  Grid grid;
  List<CustomCard> deck;

  @Before
  public void setup() {
    strategy = new MaxFlipsStrategy();
    grid = new ThreeTriosBoard(
        new GridFileReader().readFile("docs/boards/boardWithNoUnreachableCardCells.config"));
    deck = new DeckFileReader().readFile("docs/cards/AllNecessaryCards.config");
    model = new ClassicalThreeTriosModel();
    model.startGame(grid, deck, false);
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
    g.placeCard(m.getCurrentPlayerHand().remove(0), 0, 0);
    g.placeCard(m.getPlayerHand(PlayerColor.RED).remove(0), 0, 1);
    assertEquals(0, m.getPlayerHand(PlayerColor.RED).size());
    assertThrows(IllegalStateException.class, () -> strategy.getBestMove(m, PlayerColor.RED));
  }

  @Test
  public void getBestMoveFindsMaxFlips() {
    model.playTurn(0, 0, 0);
    MakePlay pred = new MakePlay(0, 1, 0);
    MakePlay best = strategy.getBestMove(model, PlayerColor.BLUE);
    System.out.println(pred.getCardInHand() + ", " + pred.getRow() + ", " + pred.getCol());
    System.out.println(best.getCardInHand() + ", " + best.getRow() + ", " + best.getCol());
    assertEquals(pred, best);
  }

  @Test
  public void getBestMoveFindsMaxFlipsWhenChain() {
    model.playTurn(0, 0, 0);
    model.playTurn(0, 1, 0);

    List<MakePlay> bestPlays = new ArrayList<>();
    bestPlays.add(new MakePlay(0, 1, 1));
    bestPlays.add(new MakePlay(1, 1, 1));
    bestPlays.add(new MakePlay(4, 1, 1));
    bestPlays.add(new MakePlay(5, 1, 1));
    bestPlays.add(new MakePlay(8, 1, 1));
    assertEquals(bestPlays, strategy.getBestMove(model, PlayerColor.RED));
    model.playTurn(1, 1, 4);
  }

  @Test
  public void getBestMoveWorksForLoneOption() {

  }
}