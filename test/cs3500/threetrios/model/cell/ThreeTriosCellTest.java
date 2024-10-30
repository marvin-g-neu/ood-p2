package cs3500.threetrios.model.cell;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * For testing constructors and implementation-specific edge cases or helpers.
 */
public class ThreeTriosCellTest {
  @Test
  public void constructorSetsUpCorrectly() {
    ThreeTriosCell ttc = new ThreeTriosCell(true);
    assertTrue(ttc.isHole());
    ttc = new ThreeTriosCell(false);
    assertFalse(ttc.isHole());
    assertTrue(ttc.isEmpty());
  }
}