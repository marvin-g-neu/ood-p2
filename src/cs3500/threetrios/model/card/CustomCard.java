package cs3500.threetrios.model.card;

/**
 * Represents a custom card in the Three Trios game, which has a name, 
 * attack values for each compass direction, and a color.
 */
public interface CustomCard {
  /**
   * Gets the name of the custom card.
   * 
   * @return the name of the custom card
   */
  public String getName();

  /**
   * Gets the attack value strength of a custom card 
   * in integer form for the north direction.
   * 
   * @return the attack value of a custom card for the north 
   */           
  public int getNorthStrength();

  /**
   * Gets the attack value strength of a custom card 
   * in integer form for the south direction.
   * 
   * @return the attack value of a custom card for the south 
   */           
  public int getSouthStrength();

  /**
   * Gets the attack value strength of a custom card 
   * in integer form for the east direction.
   * 
   * @return the attack value strength of a custom card for the east
   */           
  public int getEastStrength();

  /**
   * Gets the attack value strength of a custom card 
   * in integer form for the west direction.
   * 
   * @return the attack value strength of a custom card for the west
   */           
  public int getWestStrength();

  /**
   * Gets the color of a custom card.
   * 
   * @return the color of a custom card
   */           
  public CardColor getCurrentColor();

  /**
   * Sets the color of a custom card.
   * 
   * @param newColor the new color of a custom card
   */
  public void setNewColor(CardColor newColor);

  /**
   * Converts a custom card to a string representation.
   * 
   * @return the string representation of a custom card
   */
  public String toString();
}
