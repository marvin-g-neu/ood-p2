package cs3500.threetrios.model;

/**
 * Implementation of a Card in a game of Three Trios.
 * ThreeTriosCard objects do not track which player owns the card at any given time, so card
 * ownership must be tracked through a different class.
 * All fields in a ThreeTriosCard are unmodifiable.
 */
public class ThreeTriosCard implements Card {
  private final String name;
  private final AttackValue north;
  private final AttackValue south;
  private final AttackValue east;
  private final AttackValue west;

  /**
   * Creates a new ThreeTriosCard from the given name and attack values.
   *
   * @param name  the card's name
   * @param north the card's north attack value
   * @param south the card's south attack value
   * @param east  the card's east attack value
   * @param west  the card's west attack value
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public ThreeTriosCard(String name, AttackValue north, AttackValue south, AttackValue east,
                        AttackValue west) {
    if (name == null || north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }


  @Override
  public int getNorth() {
    return north.getValue();
  }

  @Override
  public int getSouth() {
    return south.getValue();
  }

  @Override
  public int getEast() {
    return east.getValue();
  }

  @Override
  public int getWest() {
    return west.getValue();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name + " " + north + " " + south + " " + east + " " + west;
  }
}
