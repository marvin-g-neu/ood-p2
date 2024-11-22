package cs3500.threetrios.controller;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.AttackValue;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.ThreeTriosCard;

/**
 * Configures the deck of a game of Three Trios.
 */
public class DeckConfigReader extends AbstractConfigReader {

  private final List<Card> gameCards;

  /**
   * Creates a new DeckConfigReader.
   */
  public DeckConfigReader() {
    gameCards = new ArrayList<>();
  }

  /**
   * Valid deck configuration files must have the following format:
   * CARD_NAME NORTH SOUTH EAST WEST
   * CARD_NAME NORTH SOUTH EAST WEST
   * CARD_NAME NORTH SOUTH EAST WEST
   * ...
   * Each line represents a single card in the game and consists of five strings.
   * The first string is the name of the card. The other four strings are the card's attack values.
   * The attack values are always given in the order North, South, East, West.
   *
   * @param filePath the path of the config file
   * @throws IllegalArgumentException if the file is not found or the file is in the wrong format
   */
  @Override
  public void readConfigFile(String filePath) {
    List<String> lines = super.getConfigFileText(filePath);

    // does not add directly to gameCards in case a bad card string is given
    List<ThreeTriosCard> toAdd = new ArrayList<>();
    for (String line : lines) {
      String[] split = line.split(" ");
      if (split.length != 5) {
        throw new IllegalArgumentException("Each line must have 5 strings");
      }
      String name = split[0];
      List<AttackValue> attackValues = new ArrayList<>();
      for (int stringIdx = 1; stringIdx < 5; stringIdx++) {
        attackValues.add(processAttackValue(split[stringIdx]));
      }
      toAdd.add(new ThreeTriosCard(name, attackValues.get(0), attackValues.get(1),
              attackValues.get(2), attackValues.get(3)));
    }
    gameCards.addAll(toAdd);
  }

  /**
   * Helper method to get the appropriate AttackValue from a given string.
   *
   * @param str the given string
   * @return the appropriate AttackValue
   * @throws IllegalArgumentException if the string is not 'A' or an integer 1-9
   */
  private AttackValue processAttackValue(String str) {
    int attackValue;
    try {
      if (str.equals("A")) {
        return AttackValue.A;
      }
      attackValue = Integer.parseInt(str);
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Attack values must be 'A' or an integer 1-9");
    }
    switch (attackValue) {
      case 1:
        return AttackValue.one;
      case 2:
        return AttackValue.two;
      case 3:
        return AttackValue.three;
      case 4:
        return AttackValue.four;
      case 5:
        return AttackValue.five;
      case 6:
        return AttackValue.six;
      case 7:
        return AttackValue.seven;
      case 8:
        return AttackValue.eight;
      case 9:
        return AttackValue.nine;
      default:
        throw new IllegalArgumentException("Attack values must be 'A' or an integer 1-9");
    }
  }

  /**
   * Returns the cards to be used in the game.
   *
   * @return the deck of cards for the game
   */
  public List<Card> getGameCards() {
    return new ArrayList<>(gameCards);
  }
}
