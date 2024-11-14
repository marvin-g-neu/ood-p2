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
  String getName();

  /**
   * Converts a custom card to a string representation.
   *
   * @return the string representation of a custom card
   */
  String toString();

  /**
   * Gets the attack value strength of a custom card
   * in integer form for the given direction.
   *
   * @return the attack value of a custom card for the given direction
   * @throws IllegalArgumentException if direction is null
   */
  AttackValue getAttackValue(Direction direction);

  /**
   * Gets the color of the card.
   *
   * @return the color of the card
   */
  CardColor getCurrentColor();

  /**
   * Sets the color of a custom card.
   *
   * @param newColor the new color of a custom card
   * @throws IllegalArgumentException if newColor is null
   * @throws IllegalArgumentException if the card is already this color
   */
  void setNewColor(CardColor newColor);

  /**
   * Creates and returns a copy of this card.
   *
   * @return a copy of this card
   */
  CustomCard copy();
}
