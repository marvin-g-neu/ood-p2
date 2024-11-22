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
 * Tests for GoForTheCorners.
 */
public class GoForTheCornersTest {

  private ThreeTriosStrategy strategy;
  private ThreeTriosModel<ThreeTriosCard> model;

  @Before
  public void setup() {
    strategy = new GoForTheCorners();
  }

  @Test
  public void nullParameters() {
    Assert.assertThrows("nulls in chooseMove", IllegalArgumentException.class,
        () -> strategy.chooseMove(null, null));
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
    Assert.assertEquals("3 best moves, all tied with exposed value avg of 8.5", 3,
            strategy.chooseMove(model, PlayerColor.RED).size());
    // tied moves in top left, top right, and bottom right corners.
    // Tie-break should result in top left move.
    Assert.assertEquals("move cell has row 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getRow());
    Assert.assertEquals("move cell has column 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCol());
    Assert.assertEquals("move plays card at hand index 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCardIdxInHand());
  }

  @Test
  public void noCornersOpen() {
    model = new MockModelNoCornersOpen();
    Assert.assertNull("Corners cannot be played to", strategy.chooseMove(model, PlayerColor.RED));
    // corners not open. upper-left most open cell is (0, 1).
    Assert.assertEquals("move cell has row 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getRow());
    Assert.assertEquals("move cell has column 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCol());
    Assert.assertEquals("move plays card at hand index 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCardIdxInHand());
  }

  @Test
  public void noPlayableCells() {
    model = new MockModelNoPlayableCells();
    Assert.assertNull("No open corners", strategy.chooseMove(model, PlayerColor.RED));
    Assert.assertThrows("Cannot break tie: no valid moves", IllegalStateException.class,
        () -> strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)));
  }

  @Test
  public void checkInspectedCells() {
    StringBuilder log = new StringBuilder();
    model = new MockModelRecordActions(log);
    List<Move> moves = strategy.chooseMove(model, PlayerColor.BLUE);
    Assert.assertEquals("2 best moves with avg exposed values of 9", 2, moves.size());
    // Strategy first checks that there is at least one open corner.
    // Then, for each card, checks all corners. If a corner can be played to,
    // adjacent cells are also checked (if playable). This occurs for all cards in the hand.
    Assert.assertEquals("Checks relevant cells", "Checking moves for cell: (0, 0)\n"
            + "Checking moves for cell: (0, 0)\nChecking moves for cell: (0, 1)\n"
            + "Checking moves for cell: (1, 0)\nChecking moves for cell: (0, 2)\n"
            + "Checking moves for cell: (2, 0)\nChecking moves for cell: (2, 2)\n"
            + "Checking moves for cell: (2, 1)\nChecking moves for cell: (1, 2)\n"
            + "Checking moves for cell: (0, 0)\nChecking moves for cell: (0, 1)\n"
            + "Checking moves for cell: (1, 0)\nChecking moves for cell: (0, 2)\n"
            + "Checking moves for cell: (2, 0)\nChecking moves for cell: (2, 2)\n"
            + "Checking moves for cell: (2, 1)\nChecking moves for cell: (1, 2)\n"
            + "Checking moves for cell: (0, 0)\nChecking moves for cell: (0, 1)\n"
            + "Checking moves for cell: (1, 0)\nChecking moves for cell: (0, 2)\n"
            + "Checking moves for cell: (2, 0)\nChecking moves for cell: (2, 2)\n"
            + "Checking moves for cell: (2, 1)\nChecking moves for cell: (1, 2)\n", log.toString());
  }

  @Test
  public void oneValidMove() {
    StringBuilder log = new StringBuilder();
    // only (2,2) is a valid move under the mock
    model = new MockModelOneValidMove(log);
    List<Move> moves = strategy.chooseMove(model, PlayerColor.RED);
    Assert.assertEquals("Only (2,2) is valid", "Checking moves for cell: (2, 2)\n"
            + "Checking moves for cell: (2, 2)\nChecking moves for cell: (2, 2)\n"
            + "Checking moves for cell: (2, 2)\n", log.toString());
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

    Assert.assertEquals("1 best move, avg exposed value 10, bottom right corner", 1,
            strategy.chooseMove(model, PlayerColor.RED).size());
    Assert.assertEquals("move plays card at hand index 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCardIdxInHand());
    model.playToCell(2, 2, 0);

    Assert.assertEquals("1 best move, avg exposed value 9, top left corner", 1,
            strategy.chooseMove(model, PlayerColor.BLUE).size());
    Assert.assertEquals("move plays card at hand index 2", 2,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.BLUE)).getCardIdxInHand());
    model.playToCell(0, 0, 2);

    // no more open corners, chooses uppermost leftmost card cell with card at index 0
    Assert.assertNull("no open corners", strategy.chooseMove(model, PlayerColor.RED));
    Assert.assertEquals("move cell has row 0", 0,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getRow());
    Assert.assertEquals("move cell has column 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCol());
    model.playToCell(0, 1, 0);

    Assert.assertEquals("move cell has row 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.BLUE)).getRow());
    Assert.assertEquals("move cell has column 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.BLUE)).getCol());
    model.playToCell(1, 1, 0);

    // only one possible move: (2, 1) and card index 0
    Assert.assertEquals("move cell has row 2", 2,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getRow());
    Assert.assertEquals("move cell has column 1", 1,
            strategy.breakTies(strategy.chooseMove(model, PlayerColor.RED)).getCol());
    model.playToCell(2, 1, 0);

    // game is over, Red wins 4-2
    Assert.assertEquals("Red wins", PlayerColor.RED, model.getWinner());
    Assert.assertEquals("Red wins 4-2", 4, model.getPlayerScore(PlayerColor.RED));
  }
}
