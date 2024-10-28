package cs3500.threetrios.model.cell;

import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.ThreeTriosCard;

/**
 * Represents a cell in the Three Trios game, where 
 * a cell can either be a hole, empty, or contain a 
 * card with a specific color. Note that a cell can 
 * only contain a card with a specific color if the 
 * cell is not a hole.
 */
public interface Cell {
  /**
   * Checks if a cell is a hole.
   * 
   * @return true if the cell is a hole, false otherwise
   */
  boolean isHole();     

  /**
   * Checks if a cell is empty.
   * 
   * @return true if the cell is empty, false otherwise
   */           
  boolean isEmpty();

  /**
   * Gets the color of the card in a cell.
   * 
   * @return the color of the card in the cell
   */
  CardColor getColor();

  /**
   * Gets the card in a cell.
   * 
   * @return the card in the cell
   */
  ThreeTriosCard getCard();

  /**
   * Sets the card in a cell.
   * 
   * @param card the card to set in the cell
   */
  void playCard(ThreeTriosCard card);

  /**
   * Flips the color of the card in a cell to the opponent's color.
   * 
   * @param opponentColor the color of the opponent
   */
  void flipCard(CardColor opponentColor);
}
