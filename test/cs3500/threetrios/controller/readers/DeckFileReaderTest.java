package cs3500.threetrios.controller.readers;

import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Tests for DeckFileReader readFile() implementation.
 */
public class DeckFileReaderTest {
  @Test
  public void readFileThrowsIAEWhenFileCannotBeRead() {
    assertThrows(IllegalArgumentException.class, () -> new DeckFileReader().readFile(
        "docs/cards/fakePath.config"));
    assertThrows(IllegalArgumentException.class, () -> new DeckFileReader().readFile(
        "docs/boards/boardWithNoUnreachableCardCells.config"));
  }

  @Test
  public void readFileGetsCorrectList() {
    DeckFileReader deckFileReader = new DeckFileReader();
    List<CustomCard> cards = deckFileReader.readFile("docs/cards/AllNecessaryCards.config");

    assertEquals(36, cards.size());
    CustomCard firstCard = cards.get(0);
    assertEquals("DarkMagician",firstCard.getName());
    assertEquals(AttackValue.A, firstCard.getAttackValue(Direction.NORTH));
    assertEquals(AttackValue.SIX, firstCard.getAttackValue(Direction.SOUTH));
    assertEquals(AttackValue.SEVEN, firstCard.getAttackValue(Direction.EAST));
    assertEquals(AttackValue.SEVEN, firstCard.getAttackValue(Direction.WEST));

    CustomCard lastCard = cards.get(35);
    assertEquals("MagicianOfBlackChaos",lastCard.getName());
    assertEquals(AttackValue.EIGHT, lastCard.getAttackValue(Direction.NORTH));
    assertEquals(AttackValue.A, lastCard.getAttackValue(Direction.SOUTH));
    assertEquals(AttackValue.NINE, lastCard.getAttackValue(Direction.EAST));
    assertEquals(AttackValue.SEVEN, lastCard.getAttackValue(Direction.WEST));
  }
}