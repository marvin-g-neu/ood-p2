package cs3500.threetrios.model;

/**
 * Behaviors for a Card in the game of Three Trios.
 * A card must have a name and four attack values, which range from 1-9 and A (which represents 10).
 * Any additional behaviors for cards must be made
 * creating a new interface that extends this one.
 */
public interface Card {

  /**
   * Prints the name and attack values of a card.
   * The name is printed before the attack values.
   * The attack values are printed as 1-9 or the letter A and are separated by spaces.
   * The attack values are printed in the following order: north, south, east, west.
   * Example: SampleName 1 2 3 4
   *
   * @return the string representation of the card
   */
  String toString();

  /**
   * Returns the card's north attack value.
   *
   * @return the card's north attack value
   */
  int getNorth();

  /**
   * Returns the card's south attack value.
   *
   * @return the card's south attack value
   */
  int getSouth();

  /**
   * Returns the card's east attack value.
   *
   * @return the card's east attack value
   */
  int getEast();

  /**
   * Returns the card's west attack value.
   *
   * @return the card's west attack value
   */
  int getWest();

  /**
   * Returns the name of the card.
   *
   * @return the name of the card
   */
  String getName();
}
