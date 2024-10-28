package cs3500.threetrios.model;

import cs3500.threetrios.model.cell.CellColor;
import java.util.List;

public interface TTModel {
    // Basic interface for the Three Trios game model
    // for this homework, we'll only implement the classical version
    // but we'll leave this interface so that we can easily add 
    // new versions of the game in the future into the abstract class

    /**
     * Gets the cell at a given position.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the cell at the given position
     */
    CellColor getCellAt(int row, int col);

    /**
     * Gets the current player.
     * 
     * @return the current player
     */
    String getCurrentPlayer();

    /**
     * Gets the current player's hand.
     * 
     * @return the current player's hand
     */
    List<String> getCurrentPlayerHand();
}
