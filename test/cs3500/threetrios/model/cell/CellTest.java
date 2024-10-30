package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * For tests of common functionality among implementations.
 */
public class CellTest {
  Cell ttcHole;
  Cell ttcCell;
  CustomCard red;
  AttackValue one = AttackValue.ONE;

  @Before
  public void setUp() {
    ttcHole = new ThreeTriosCell(true);
    ttcCell = new ThreeTriosCell(false);
    red = new ThreeTriosCard("Red", one, one, one, one, CardColor.RED);
  }

  @Test
  public void isHoleWorksCorrectly() {
    assertTrue(ttcHole.isHole());
    assertFalse(ttcCell.isHole());
  }

  @Test
  public void isEmptyWorksCorrectly() {
    assertTrue(ttcCell.isEmpty());
    assertThrows(IllegalStateException.class, () -> ttcHole.isEmpty());
    ttcCell.playCard(red);
    assertFalse(ttcCell.isEmpty());
    ttcCell.flipCard(CardColor.BLUE);
    assertFalse(ttcCell.isEmpty());
  }

  @Test
  public void getCellColorThrowsForEmptyOrHole() {
    assertThrows(IllegalStateException.class, () -> ttcCell.getCellColor());
    assertThrows(IllegalStateException.class, () -> ttcHole.getCellColor());
  }

  @Test
  public void getCellColorWorksCorrectly() {
    ttcCell.playCard(red);
    assertEquals(PlayerColor.RED, ttcCell.getCellColor());
    ttcCell.flipCard(CardColor.BLUE);
    assertEquals(PlayerColor.BLUE, ttcCell.getCellColor());
  }

  @Test
  public void getCellStateWorksCorrectly() {
    assertEquals(CellState.HOLE, ttcHole.getCellState());
    assertEquals(CellState.EMPTY, ttcCell.getCellState());
    ttcCell.playCard(red);
    assertEquals(CellState.RED, ttcCell.getCellState());
    ttcCell.flipCard(CardColor.BLUE);
    assertEquals(CellState.BLUE, ttcCell.getCellState());
  }

  @Test
  public void getCardThrowsForEmptyOrHole() {
    assertThrows(IllegalStateException.class, () -> ttcCell.getCard());
    assertThrows(IllegalStateException.class, () -> ttcHole.getCard());
  }

  @Test
  public void getCardWorksCorrectly() {
    ttcCell.playCard(red);
    assertEquals(red, ttcCell.getCard());
    ttcCell.flipCard(CardColor.BLUE);
    assertEquals(CardColor.BLUE, ttcCell.getCard().getCurrentColor());
  }

  @Test
  public void playCardThrowsWhenExpected() {
    assertThrows(IllegalArgumentException.class, () -> ttcCell.playCard(null));
    assertThrows(IllegalArgumentException.class,
        () -> ttcCell.playCard(new ThreeTriosCard("Unassigned", one, one, one, one)));
    assertThrows(IllegalStateException.class, () -> ttcHole.playCard(red));
    ttcCell.playCard(red);
    assertThrows(IllegalStateException.class, () ->
        ttcCell.playCard(new ThreeTriosCard("B", one, one, one, one, CardColor.BLUE)));
  }

  @Test
  public void playCardWorksCorrectly() {
    ttcCell.playCard(red);
    assertEquals(red, ttcCell.getCard());
  }

  @Test
  public void flipCardThrowsWhenExpected() {
    assertThrows(IllegalArgumentException.class, () -> ttcCell.flipCard(null));
    assertThrows(IllegalStateException.class, () -> ttcCell.flipCard(CardColor.BLUE));
    ttcCell.playCard(red);
    assertThrows(IllegalArgumentException.class, () -> ttcCell.flipCard(CardColor.UNASSIGNED));
    assertThrows(IllegalArgumentException.class, () -> ttcCell.flipCard(CardColor.RED));
    assertThrows(IllegalStateException.class, () -> ttcHole.flipCard(CardColor.RED));
  }

  @Test
  public void flipCardWorksCorrectly() {
    ttcCell.playCard(red);
    ttcCell.flipCard(CardColor.BLUE);
    assertEquals(PlayerColor.BLUE, ttcCell.getCellColor());
    assertEquals(CardColor.BLUE, ttcCell.getCard().getCurrentColor());
    ttcCell.flipCard(CardColor.RED);
    assertEquals(PlayerColor.RED, ttcCell.getCellColor());
    assertEquals(CardColor.RED, ttcCell.getCard().getCurrentColor());
  }
}