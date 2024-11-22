package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests implementation specific details of the Cell class for a game of Three Trios.
 */
public class CellTest {

  Cell sampleCell;
  ThreeTriosCard sampleCard = new ThreeTriosCard("CorruptKing", AttackValue.seven,
          AttackValue.three, AttackValue.nine, AttackValue.A);

  @Test
  public void constructorTest() {
    Assert.assertThrows("Null cell type", NullPointerException.class, () -> new Cell(null));
    sampleCell = new Cell(CellType.CARDCELL);
    Assert.assertEquals("correct cell type", CellType.CARDCELL, sampleCell.getCellType());
    Assert.assertNull("no card yet", sampleCell.getCard());
    Assert.assertNull("no owner yet", sampleCell.getOwner());
  }

  @Test
  public void setCardTest() {
    sampleCell = new Cell(CellType.HOLE);
    Assert.assertThrows("can't set card to a hole", IllegalStateException.class,
        () -> sampleCell.setCard(sampleCard));
    sampleCell = new Cell(CellType.CARDCELL);
    sampleCell.setCard(sampleCard);
    Assert.assertEquals("card set correctly", sampleCard, sampleCell.getCard());
  }

  @Test
  public void setOwnerTest() {
    sampleCell = new Cell(CellType.HOLE);
    Assert.assertThrows("can't set owner to a hole", IllegalStateException.class,
        () -> sampleCell.setOwner(PlayerColor.RED));
    sampleCell = new Cell(CellType.CARDCELL);
    Assert.assertThrows("can't set owner of unoccupied cell", IllegalStateException.class,
        () -> sampleCell.setOwner(PlayerColor.RED));
    sampleCell.setCard(sampleCard);
    sampleCell.setOwner(PlayerColor.RED);
    Assert.assertEquals("owner set correctly", PlayerColor.RED, sampleCell.getOwner());
  }
}
