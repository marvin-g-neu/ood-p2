package cs3500.threetrios.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelImplementation;

/**
 * Tests for FlipMostCards.
 */
public class FlipMostCardsTest {

  private ThreeTriosStrategy strategy;
  private ThreeTriosModel<ThreeTriosCard> model;

  @Before
  public void setup() {
    strategy = new FlipMostCards();
  }

  @Test
  public void nullParameters() {
    Assert.assertThrows("nulls in chooseMove", IllegalArgumentException.class,
        () -> strategy.chooseMove(null, null));
    model = new ThreeTriosModelImplementation();
    model.startGame("gridConfigFiles" + File.separator + "5x3AllCardCells.txt",
            "deckConfigFiles" + File.separator + "bigDeck.txt");
    strategy.chooseMove(model, PlayerColor.RED);
    Assert.assertThrows("no open cells", IllegalStateException.class,
        () -> strategy.breakTies(null));
  }

  @Test (expected = IllegalStateException.class)
  public void moveNotChosenYet() {
    strategy.breakTies(List.of(new Move(0, 0, 0)));
  }

  @Test
  public void allCardCellsOpen() {
    model = new ThreeTriosModelImplementation();
    model.startGame("gridConfigFiles" + File.separator + "5x3AllCardCells.txt",
            "deckConfigFiles" + File.separator + "bigDeck.txt");
    Assert.assertEquals("120 possible moves, all tied with 0 potential flips", 120,
            strategy.chooseMove(model, PlayerColor.RED).size());
    Assert.assertEquals("move cell has row 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getRow());
    Assert.assertEquals("move cell has column 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCol());
    Assert.assertEquals("move plays card at hand index 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCardIdxInHand());
  }

  @Test
  public void noPlayableCells() {
    model = new MockModelNoPlayableCells();
    Assert.assertNull("No valid moves", strategy.chooseMove(model, PlayerColor.RED));
    Assert.assertThrows("Cannot break tie: no valid moves", IllegalStateException.class,
        () -> strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)));
  }

  @Test
  public void checkInspectedCells() {
    StringBuilder log = new StringBuilder();
    model = new MockModelRecordActions(log);
    List<Move> moves = strategy.chooseMove(model, PlayerColor.BLUE);
    Assert.assertEquals("3 moves can flip Red's card", 3, moves.size());
    Assert.assertEquals("Checks all cells", "Checking moves for cell: (0, 0)\n"
            + "Checking moves for cell: (0, 1)\nChecking moves for cell: (0, 2)\n"
            + "Checking moves for cell: (1, 0)\nChecking moves for cell: (1, 1)\n"
            + "Checking moves for cell: (1, 2)\nChecking moves for cell: (2, 0)\n"
            + "Checking moves for cell: (2, 1)\nChecking moves for cell: (2, 2)\n", log.toString());
  }

  @Test
  public void oneValidMove() {
    StringBuilder log = new StringBuilder();
    // only (2,2) is a valid move under the mock
    model = new MockModelOneValidMove(log);
    List<Move> moves = strategy.chooseMove(model, PlayerColor.RED);
    Assert.assertEquals("Only (2,2) is valid", "Checking moves for cell: (2, 2)\n", log.toString());
    Move best = strategy.breakTies(moves);
    Assert.assertEquals("move cell has row 2", 2, best.getRow());
    Assert.assertEquals("move cell has column 2", 2, best.getCol());
    Assert.assertEquals("move plays card at hand index 0", 0, best.getCardIdxInHand());
  }

  @Test
  public void gameInProgress() {
    model = new ThreeTriosModelImplementation();
    model.startGame("gridConfigFiles" + File.separator + "3x3AllCardCellsTouch.txt",
            "deckConfigFiles" + File.separator + "smallDeck.txt");
    model.playToCell(0, 0, 2);
    Assert.assertEquals("2 moves flip card", 2,
            strategy.chooseMove(model, PlayerColor.BLUE).size());
    Assert.assertEquals("move plays card at hand index 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.BLUE)).getCardIdxInHand());
    model.playToCell(0, 1, 1);
    Assert.assertEquals("1 best move, flips 2 cards (1,1,0)", 1,
            strategy.chooseMove(model, PlayerColor.RED).size());
    model.playToCell(1, 1, 0);
    Assert.assertEquals("2 best moves, flip 3 cards (both at (2,1))", 2,
            strategy.chooseMove(model, PlayerColor.BLUE).size());
    Assert.assertEquals("move plays card at hand index 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.BLUE)).getCardIdxInHand());
    model.playToCell(2, 1, 0);
    Assert.assertEquals("1 possible move: (2,2,0) flips 4 cards", 1,
            strategy.chooseMove(model, PlayerColor.RED).size());
    model.playToCell(2, 2, 0);
    // game is over, Red owns all cards on board
    Assert.assertEquals("Red wins", PlayerColor.RED, model.getWinner());
    Assert.assertEquals("Red wins 5-1", 5, model.getPlayerScore(PlayerColor.RED));
  }
}
