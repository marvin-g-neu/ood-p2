package cs3500.threetrios.model.readers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.ThreeTriosCard;

/**
 * Reads and parses card database configuration files 
 * for the Three Trios game, given in the following format:
 * CARD_NAME NORTH SOUTH EAST WEST
 * CARD_NAME NORTH SOUTH EAST WEST
 * CARD_NAME NORTH SOUTH EAST WEST
 * ...
 */
public class CardReader {
  /**
   * Reads a card database file and creates a list of cards.
   *
   * @param filePath the path to the card database file
   * @return a list of ThreeTriosCards
   * @throws IllegalArgumentException if the file format is invalid or file cannot be read
   */
  public static List<ThreeTriosCard> readCards(String filePath) throws IllegalArgumentException {
    try (FileReader reader = new FileReader(filePath);
         Scanner scanner = new Scanner(reader)) {
      
      // Create deck of cards
      List<ThreeTriosCard> cardDeck = new ArrayList<>();
      
      // Read and parse each line
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
          continue;
        }
        
        // Ensure valid format for each line
        String[] parts = line.split("\\s+");
        if (parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid card format: each line must have name and 4 attack values");
        }
        
        // Parse card name and attack values
        String name = parts[0];
        AttackValue[] values = new AttackValue[4];
        
        // Parse attack values
        for (int i = 1; i < 5; i++) {
          try {
            values[i] = getAttackValue(parts[i]);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid attack value: " + parts[i] + " for card " + name);
          }
        }
        
        // Create card and add to the deck
        cardDeck.add(new ThreeTriosCard(name, values[0], values[1], values[2], values[3]));
      }
      
      // Return the deck of cards
      return cardDeck;
    } catch (IOException e) {
      throw new IllegalArgumentException("Error reading card file: " + e.getMessage());
    }
  }
  
  /**
   * Converts an integer to its corresponding AttackValue.
   *
   * @param value the integer value
   * @return the corresponding AttackValue
   * @throws IllegalArgumentException if the value is not between 1 and 9
   */
  private static AttackValue getAttackValue(String value) {
    switch (value) {
      case "1":
        return AttackValue.ONE;
      case "2":
        return AttackValue.TWO;
      case "3":
        return AttackValue.THREE;
      case "4":
        return AttackValue.FOUR;
      case "5":
        return AttackValue.FIVE;
      case "6":
        return AttackValue.SIX;
      case "7":
        return AttackValue.SEVEN;
      case "8":
        return AttackValue.EIGHT;
      case "9":
        return AttackValue.NINE;
      case "A":
        return AttackValue.A;
      default:
        throw new IllegalArgumentException();
    }
  }
}
