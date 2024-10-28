package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.ThreeTriosCard;

/**
 * {@inheritDoc}
 */
public class ThreeTriosCell implements Cell {
  private final boolean isHole;
  private ThreeTriosCard card;
  
  /**
   * Constructs a ThreeTriosCell with a boolean for whether 
   * the cell is a hole.
   * 
   * @param isHole whether the cell is a hole
   */
  public ThreeTriosCell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isHole() { 
    return isHole; 
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() { 
    return !isHole && card == null; 
  }
  
 /**
   * {@inheritDoc}
   */
  @Override
  public CardColor getColor() {
    return card != null ? card.getCurrentColor() : CardColor.UNASSIGNED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ThreeTriosCard getCard() { 
    return card; 
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void playCard(ThreeTriosCard card) {
    if (!isHole) {
      this.card = card;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flipCard(CardColor opponentColor) {
    if (card != null) {
      card.setNewColor(opponentColor);
    }
  }
}
