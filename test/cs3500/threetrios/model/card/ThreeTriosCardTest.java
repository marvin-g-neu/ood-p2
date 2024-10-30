package cs3500.threetrios.model.card;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

/**
 * For testing constructors and implementation-specific edge cases or helpers.
 */
public class ThreeTriosCardTest {
  AttackValue avl = AttackValue.A;
  CardColor clr = CardColor.RED;
  String name = "N";

  @Test
  public void constructorsThrowForNullValues() {
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(null, avl, avl, avl, avl));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, null, avl, avl, avl));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, null, avl, avl));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, avl, null, avl));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, avl, avl, null));

    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(null, avl, avl, avl, avl, clr));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, null, avl, avl, avl, clr));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, null, avl, avl, clr));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, avl, null, avl, clr));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, avl, avl, null, clr));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard(name, avl, avl, avl, avl, null));
  }

  @Test
  public void constructorsThrowForEmptyString() {
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard("", avl, avl, avl, avl));
    assertThrows(IllegalArgumentException.class,
        () -> new ThreeTriosCard("", avl, avl, avl, avl, clr));
  }
}