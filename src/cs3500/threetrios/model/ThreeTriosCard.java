package cs3500.threetrios.model;

/**
 * {@inheritDoc}
 */
public class ThreeTriosCard implements CustomCard {
  private final String name;
  private final AttackValue north;
  private final AttackValue south;
  private final AttackValue east;
  private final AttackValue west;
  private CardColor currentColor;
  
  /**
   * Constructs a ThreeTriosCard with the given name and attack values.
   * 
   * @param name the name of the custom card
   * @param north the attack value of the custom card for the north direction
   * @param south the attack value of the custom card for the south direction
   * @param east the attack value of the custom card for the east direction
   * @param west the attack value of the custom card for the west direction
   */
  public ThreeTriosCard(String name, 
                         AttackValue north, 
                         AttackValue south, 
                         AttackValue east, 
                         AttackValue west) {
    if (name == null || north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Name and/or attack values cannot be null");
    }
    // assigns the name and attack values to the card
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;

    // the default color is UNASSIGNED until a player 
    // is given the card or the card is flipped to 
    // the opposing player
    this.currentColor = CardColor.UNASSIGNED;
  }

  /**
   * Constructs a ThreeTriosCard with the given name, attack values, and color.
   * 
   * @param name the name of the custom card
   * @param north the attack value of the custom card for the north direction
   * @param south the attack value of the custom card for the south direction
   * @param east the attack value of the custom card for the east direction
   * @param west the attack value of the custom card for the west direction
   * @param currentColor the color of the custom card
   */
  public ThreeTriosCard(String name, 
                         AttackValue north, 
                         AttackValue south, 
                         AttackValue east, 
                         AttackValue west,
                         CardColor currentColor) {
    if (name == null || north == null || south == null || east == null || west == null || currentColor == null) {
      throw new IllegalArgumentException("Name and/or attack values and/or current color cannot be null");
    }
    // assigns the name and attack values to the card
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    // the color is set to the color of the player
    this.currentColor = currentColor;
  }
  
  // Getters and setters

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNorthStrength() { 
    return north.getStrength(); 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSouthStrength() { 
    return south.getStrength(); 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getEastStrength() { 
    return east.getStrength(); 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWestStrength() { 
    return west.getStrength(); 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() { 
    return name; 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CardColor getCurrentColor() { 
    return currentColor; 
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNewColor(CardColor newColor) { 
    this.currentColor = newColor; 
  }
}
