package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests implementation specific details of ThreeTriosCard for a game of Three Trios.
 */
public class ThreeTriosCardTest {

  @Test
  public void constructorTest() {
    Assert.assertThrows("null parameters", IllegalArgumentException.class,
        () -> new ThreeTriosCard(null, null, null, null, null));
    ThreeTriosCard sampleCard = new ThreeTriosCard("sample", AttackValue.one, AttackValue.two,
            AttackValue.three, AttackValue.four);
    Assert.assertEquals("correct name", "sample", sampleCard.getName());
    Assert.assertEquals("correct north", 1, sampleCard.getNorth());
    Assert.assertEquals("correct south", 2, sampleCard.getSouth());
    Assert.assertEquals("correct east", 3, sampleCard.getEast());
    Assert.assertEquals("correct west", 4, sampleCard.getWest());
  }

  @Test
  public void cardToStringTest() {
    ThreeTriosCard sampleCard = new ThreeTriosCard("sample", AttackValue.one, AttackValue.two,
            AttackValue.three, AttackValue.four);
    Assert.assertEquals("correct string representation of card", "sample 1 2 3 4",
            sampleCard.toString());
  }
}
