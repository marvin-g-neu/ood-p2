package cs3500.threetrios.model.card;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * For tests of common functionality among implementations, and tests of used enums.
 */
public class CustomCardTest {
  CustomCard ttcBob;

  @Before
  public void setUp() {
    ttcBob = new ThreeTriosCard("Bob", AttackValue.THREE, AttackValue.TWO,
        AttackValue.A, AttackValue.ONE);
  }

  @Test
  public void getNameGetsName() {
    assertEquals("Bob", ttcBob.getName());
  }

  @Test
  public void toStringConvertsToStringCorrectly() {
    assertEquals("Bob 3 2 A 1", ttcBob.toString());
  }

  @Test
  public void getAttackValueGetsCorrectValue() {
    assertEquals(AttackValue.THREE, ttcBob.getAttackValue(Direction.NORTH));
    assertEquals(AttackValue.TWO, ttcBob.getAttackValue(Direction.SOUTH));
    assertEquals(AttackValue.A, ttcBob.getAttackValue(Direction.EAST));
    assertEquals(AttackValue.ONE, ttcBob.getAttackValue(Direction.WEST));
  }

  @Test
  public void getAttackValueThrowsForNullDir() {
    assertThrows(IllegalArgumentException.class,
        () -> ttcBob.getAttackValue(null));
  }

  @Test
  public void getCurrentColorGetsCorrectColor() {
    assertEquals(ttcBob.getCurrentColor(), CardColor.UNASSIGNED);
    ttcBob.setNewColor(CardColor.RED);
    assertEquals(ttcBob.getCurrentColor(), CardColor.RED);
    ttcBob.setNewColor(CardColor.BLUE);
    assertEquals(ttcBob.getCurrentColor(), CardColor.BLUE);
  }

  @Test
  public void setNewColorThrowsForNullColor() {
    assertThrows(IllegalArgumentException.class,
        () -> ttcBob.setNewColor(null));
  }

  @Test
  public void setNewColorThrowsForSameColor() {
    ttcBob.setNewColor(CardColor.RED);
    assertThrows(IllegalArgumentException.class,
        () -> ttcBob.setNewColor(CardColor.RED));
  }

  // Enum tests

  @Test
  public void attackValueGetStrengthGetsCorrectStrength() {
    assertEquals(3, AttackValue.THREE.getStrength());
    assertEquals(10, AttackValue.A.getStrength());
  }

  @Test
  public void attackValueToStringWorksCorrectly() {
    assertEquals("3", AttackValue.THREE.toString());
    assertEquals("A", AttackValue.A.toString());
  }

  @Test
  public void cardColorGetColorReturnsCorrectColor() {
    assertEquals("RED", CardColor.RED.toString());
    assertEquals("BLUE", CardColor.BLUE.toString());
    assertEquals("UNASSIGNED", CardColor.UNASSIGNED.toString());
  }
}