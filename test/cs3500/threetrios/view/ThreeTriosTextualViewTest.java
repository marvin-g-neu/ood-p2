package cs3500.threetrios.view;

import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test of the interface methods in implementations of ClassicalThreeTriosModel.
 */
public class ThreeTriosTextualViewTest {
  private ClassicalThreeTriosModel model;
  private ThreeTriosView view;
  private Appendable out;

  @Before
  public void setUp() {
    Cell[][] cells = new Cell[3][3];
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        cells[r][c] = new ThreeTriosCell(false);
      }
    }

    // Initialize grid
    Grid grid = new ThreeTriosBoard(cells);

    // Initialize deck with 10 cards
    List<CustomCard> deck = Arrays.asList(
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

    // Initialize model
    model = new ClassicalThreeTriosModel();

    // Start the game
    model.startGame(grid, deck, false);

    out = new StringBuilder();
    // Initialize view
    view = new ThreeTriosTextualView(model, out);
  }

  @Test
  public void testRenderInitialState() {
    String expected = "Player: RED\n"
        + "___\n"
        + "___\n"
        + "___\n"
        + "Hand:\n"
        + "Red1 3 2 1 1\n"
        + "Red2 4 1 2 3\n"
        + "Red3 2 2 3 1\n"
        + "Red4 1 3 2 4\n"
        + "Red5 3 4 1 2\n";
    try {
      view.render();
    } catch (IOException e) {
      Assert.fail();
    }
    assertEquals(expected, out.toString());
  }

  @Test
  public void testRenderAfterMoves() {
    // Red plays Red1 at (0,0)
    model.playTurn(0, 0, 0);

    // Blue plays Blue1 at (0,1)
    model.playTurn(0, 1, 0);

    // Red plays Red2 at (1,1)
    model.playTurn(1, 1, 2);

    // Blue plays Blue2 at (2,2)
    model.playTurn(2, 2, 0);

    System.out.println(view.toString());

    String expected = "Player: RED\n"
        + "RB_\n"
        + "_R_\n"
        + "__B\n"
        + "Hand:\n"
        + "Red2 4 1 2 3\n"
        + "Red3 2 2 3 1\n"
        + "Red5 3 4 1 2\n";
    try {
      view.render();
    } catch (IOException e) {
      Assert.fail();
    }
    assertEquals(expected, out.toString());
  }

  @Test
  public void testRenderAfterInvalidMove() {
    // Attempt to play at invalid position
    try {
      model.playTurn(3, 3, 0);
    } catch (IllegalArgumentException e) {
      // Expected exception
    }

    // Render should remain the same as initial
    String expected = "Player: RED\n"
        + "___\n"
        + "___\n"
        + "___\n"
        + "Hand:\n"
        + "Red1 3 2 1 1\n"
        + "Red2 4 1 2 3\n"
        + "Red3 2 2 3 1\n"
        + "Red4 1 3 2 4\n"
        + "Red5 3 4 1 2\n";

    try {
      view.render();
    } catch (IOException e) {
      Assert.fail();
    }
    assertEquals(expected, out.toString());
  }

  @Test
  public void testRenderAfterFlipCard() {
    CustomCard redFirst = model.getCurrentPlayerHand().get(0);
    // Red plays Red1 at (0,0)
    model.playTurn(0, 0, 0);

    CustomCard blueFirst = model.getCurrentPlayerHand().get(0);
    // Blue plays Blue1 at (0,1)
    model.playTurn(0, 1, 0);

    CustomCard redSecond = model.getCurrentPlayerHand().get(0);
    // Red plays Red2 at (1,1)
    model.playTurn(1, 1, 0);

    CustomCard blueSecond = model.getCurrentPlayerHand().get(0);
    // Blue plays Blue2 at (0,0) to battle Red1
    model.playTurn(1, 2, 0);

    boolean blueWinFirst = blueFirst.getAttackValue(Direction.WEST).getStrength()
        > redFirst.getAttackValue(Direction.EAST).getStrength();
    boolean redWinSecond = redSecond.getAttackValue(Direction.NORTH).getStrength()
        > blueFirst.getAttackValue(Direction.SOUTH).getStrength();
    boolean blueWinThird = blueSecond.getAttackValue(Direction.WEST).getStrength()
        > redSecond.getAttackValue(Direction.EAST).getStrength();
    String expected = getExpectedOutput(blueWinFirst, redWinSecond, blueWinThird);

    try {
      view.render();
    } catch (IOException e) {
      Assert.fail();
    }
    assertEquals(expected, out.toString());
  }

  private static String getExpectedOutput(boolean blueWinFirst,
                                          boolean redWinSecond, boolean blueWinThird) {
    String firstBattle;
    String secondBattle;

    if (blueWinFirst) {
      if (redWinSecond && !blueWinThird) {
        firstBattle = "BR";
      } else {
        firstBattle = "BB";
      }
    } else {
      if (redWinSecond) {
        if (blueWinThird) {
          firstBattle = "RB";
        } else {
          firstBattle = "RR";
        }
      } else {
        firstBattle = "RB";
      }
    }
    if (blueWinThird) {
      secondBattle = "BB";
    } else {
      secondBattle = "RB";
    }

    return "Player: RED\n" + firstBattle
        + "_\n"
        + "_" + secondBattle + "\n"
        + "___\n"
        + "Hand:\n"
        + "Red3 2 2 3 1\n"
        + "Red4 1 3 2 4\n"
        + "Red5 3 4 1 2\n";
  }
}