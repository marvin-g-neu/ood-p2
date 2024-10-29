package cs3500.threetrios.model.card;

/**
 * A basic card implementation for the game Three Trios.
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
   * @param name  the name of the custom card
   * @param north the attack value of the custom card for the north direction
   * @param south the attack value of the custom card for the south direction
   * @param east  the attack value of the custom card for the east direction
   * @param west  the attack value of the custom card for the west direction
   * @throws IllegalArgumentException if any parameters are null
   */
  public ThreeTriosCard(String name,
                        AttackValue north, AttackValue south, AttackValue east, AttackValue west) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Attack values cannot be null");
    }
    // assigns the name and attack values to the card
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;

    // the default color is UNASSIGNED until a player is given the card
    this.currentColor = CardColor.UNASSIGNED;
  }

  /**
   * Constructs a ThreeTriosCard with the given name, attack values, and color.
   *
   * @param name         the name of the custom card
   * @param north        the attack value of the custom card for the north direction
   * @param south        the attack value of the custom card for the south direction
   * @param east         the attack value of the custom card for the east direction
   * @param west         the attack value of the custom card for the west direction
   * @param currentColor the color of the custom card
   * @throws IllegalArgumentException if any parameter is null
   */
  public ThreeTriosCard(String name,
                        AttackValue north,
                        AttackValue south,
                        AttackValue east,
                        AttackValue west,
                        CardColor currentColor) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (currentColor == null) {
      throw new IllegalArgumentException("Current color cannot be null");
    }
    if (north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Attack values cannot be null");
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

  @Override
  public AttackValue getStrength(Direction direction) {
    switch (direction) {
      case NORTH:
        return north;
      case SOUTH:
        return south;
      case EAST:
        return east;
      case WEST:
        return west;
    }
    throw new IllegalArgumentException("Invalid direction");
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public CardColor getCurrentColor() {
    return currentColor;
  }

  @Override
  public void setNewColor(CardColor newColor) {
    this.currentColor = newColor;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s %s %s", name, north, south, east, west);
  }
}
