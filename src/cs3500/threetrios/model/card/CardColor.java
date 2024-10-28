package cs3500.threetrios.model.card;

/**
 * Represents the color of a card, either RED, BLUE, 
 * or UNASSIGNED if the card has not been assigned to a player.
 */
public enum CardColor {
  RED("RED"),
  BLUE("BLUE"), 
  UNASSIGNED("UNASSIGNED");

  // the color of the card
  private final String color;

  /**
   * Constructs a CardColor with the given color.
   * 
   * @param color the color of the card
   */
  CardColor(String color) {
    this.color = color;
  }

  /**
   * Gets the color of the card.
   * 
   * @return the color of the card
   */
  public String getColor() {
    return color;
  }
}
