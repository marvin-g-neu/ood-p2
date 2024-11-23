package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.ThreeTriosTextualView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests of the implemented methods in BasicThreeTriosGame.
 */
public class BasicThreeTriosGameTest {
  private ThreeTriosModelInterface model;
  private RuleKeeper rules;
  private Cell testCell;
  private CustomCard testCard;
  private ThreeTriosView view;

  @Before
  public void setUp() {
    // Initialize grid with a simple 3x3 board with no holes
    Cell[][] cells = new Cell[3][3];
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        cells[r][c] = new ThreeTriosCell(false);
      }
    }
    Grid grid = new ThreeTriosBoard(cells);

    // Initialize deck with 10 cards (similar to TextualViewTest)
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

    // Initialize model and rules
    model = new ClassicalThreeTriosModel();
    model.startGame(grid, deck, false);
    rules = new BasicThreeTriosGame(model);

    // Initialize test cell and card for individual tests
    testCell = new ThreeTriosCell(false);
    testCard = new ThreeTriosCard("Test", AttackValue.THREE, AttackValue.TWO,
        AttackValue.ONE, AttackValue.ONE, CardColor.RED);

    // Render the board to the console via the view for debugging
    view = new ThreeTriosTextualView(model, System.out);
  }

  // Tests for isLegalMove
  @Test
  public void testIsLegalMoveValidMove() {
    assertTrue(rules.isLegalMove(testCell, testCard));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsLegalMoveNullCell() {
    rules.isLegalMove(null, testCard);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsLegalMoveNullCard() {
    rules.isLegalMove(testCell, null);
  }

  @Test
  public void testIsLegalMoveHoleCell() {
    Cell holeCell = new ThreeTriosCell(true);
    assertFalse(rules.isLegalMove(holeCell, testCard));
  }

  @Test
  public void testIsLegalMoveOccupiedCell() {
    testCell.playCard(testCard);
    assertFalse(rules.isLegalMove(testCell, testCard));
  }

  // Tests for getOppositeDirection
  @Test
  public void testGetOppositeDirection() {
    assertEquals(Direction.SOUTH, rules.getOppositeDirection(Direction.NORTH));
    assertEquals(Direction.NORTH, rules.getOppositeDirection(Direction.SOUTH));
    assertEquals(Direction.WEST, rules.getOppositeDirection(Direction.EAST));
    assertEquals(Direction.EAST, rules.getOppositeDirection(Direction.WEST));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOppositeDirectionNull() {
    rules.getOppositeDirection(null);
  }

  // Tests for attackerWinsBattle
  @Test
  public void testAttackerWinsBattle() {
    CustomCard attacker = new ThreeTriosCard("Attacker", AttackValue.FOUR,
        AttackValue.ONE, AttackValue.ONE, AttackValue.ONE, CardColor.RED);
    CustomCard defender = new ThreeTriosCard("Defender", AttackValue.THREE,
        AttackValue.ONE, AttackValue.ONE, AttackValue.ONE, CardColor.BLUE);

    assertTrue(rules.attackerWinsBattle(attacker, defender, Direction.NORTH));
  }

  @Test
  public void testAttackerLosesBattle() {
    CustomCard attacker = new ThreeTriosCard("Attacker", AttackValue.THREE,
        AttackValue.ONE, AttackValue.ONE, AttackValue.ONE, CardColor.BLUE);
    CustomCard defender = new ThreeTriosCard("Defender", AttackValue.FOUR,
        AttackValue.ONE, AttackValue.ONE, AttackValue.ONE, CardColor.RED);

    assertFalse(rules.attackerWinsBattle(attacker, defender, Direction.SOUTH));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackerWinsBattleNullAttacker() {
    rules.attackerWinsBattle(null, testCard, Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackerWinsBattleNullDefender() {
    rules.attackerWinsBattle(testCard, null, Direction.NORTH);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttackerWinsBattleNullDirection() {
    rules.attackerWinsBattle(testCard, testCard, null);
  }

  // Tests for executeBattlePhase
  @Test
  public void testExecuteBattlePhaseSimple() {
    // Place initial cards
    model.playTurn(0, 0, 0); // Red1
    model.playTurn(0, 1, 0); // Blue1

    assertEquals(CardColor.RED.getColor(), model.getCellStateAt(0, 0).getName());
    assertEquals(CardColor.BLUE.getColor(), model.getCellStateAt(0, 1).getName());
  }

  @Test
  public void testExecuteBattlePhaseComboFlip() {
    try {
      // Setup a scenario for combo flips
      view.render();
      model.playTurn(1, 1, 0);
      view.render();
      model.playTurn(2, 2, 4);
      view.render();
      model.playTurn(1, 0, 0);
      view.render();
      model.playTurn(2, 1, 0);
      view.render();

      // combo flip move
      model.playTurn(1, 2, 1);
      view.render();

      // Verify the combo flip occurred
      assertEquals(CardColor.RED.getColor(), model.getCellStateAt(2, 2).getName());
      assertEquals(CardColor.RED.getColor(), model.getCellStateAt(2, 1).getName());
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBattlePhaseNullPlayer() {
    rules.executeBattlePhase(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteBattlePhaseInvalidCoordinates() {
    rules.executeBattlePhase(-1, 0, PlayerColor.RED);
  }

  // Tests for isGameCompleted
  @Test
  public void testIsGameCompletedEmpty() {
    assertFalse(rules.isGameCompleted());
  }

  @Test
  public void testIsGameCompletedFull() {
    // Fill the entire board
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (model.getCellStateAt(i, j) == CellState.EMPTY) {
          model.playTurn(i, j, 0);
        }
      }
    }
    assertTrue(rules.isGameCompleted());
  }

  @Test
  public void testIsGameCompletedPartial() {
    // Fill only part of the board
    model.playTurn(0, 0, 0);
    model.playTurn(0, 1, 0);
    assertFalse(rules.isGameCompleted());
  }
}
