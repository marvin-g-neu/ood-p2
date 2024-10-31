# Player Interface Design for Three Trios

## Overview
The Three Trios game requires a flexible player interface that can support both human and AI players. This document outlines the design for player interactions and how they integrate with the existing model.

## Player Interface

```java
/**
 * Represents a player in the Three Trios game.
 */
public interface Player {
  /**
   * Gets the color assigned to this player (RED or BLUE).
   */
  PlayerColor getColor();

  /**
   * Selects a card from the player's hand.
   * @return index of the selected card in the player's hand
   * @throws IllegalStateException if player's hand is empty
   */
  int selectCard();

  /**
   * Chooses coordinates for card placement.
   * @return int array containing [row, col] coordinates
   * @throws IllegalStateException if no valid moves are available
   */
  int[] selectCell();
}
```

## Example Usage

```java
// Initialize model and configure the grid and deck
ClassicalThreeTriosModel model = new ClassicalThreeTriosModel(true);

String gridWithNoReachableCardCells = "docs" + File.separator + "boards" + File.separator + "boardWithNoReachableCardCells.config";
String deckWithSomeCardsMissing = "docs" + File.separator + "cards" + File.separator + "someCards.config";

Grid grid = new GridFileReader().readFile(gridWithNoReachableCardCells);
List<CustomCard> deck = new DeckFileReader().readFile(deckWithSomeCardsMissing);

// Create players (HumanPlayer or AIPlayer both implement the Player interface)
Player redPlayer = new HumanPlayer(PlayerColor.RED, model);
Player bluePlayer = new AIPlayer(PlayerColor.BLUE, model);

// Start the game using the grid and deck
model.startGame(grid, deck);

// Game loop
while (model.getGameState() == GameState.IN_PROGRESS) {
  Player currentPlayer = (model.getCurrentPlayer() == PlayerColor.RED) 
    ? redPlayer 
    : bluePlayer;
  try {
    // Get player move
    int cardIndex = currentPlayer.selectCard();
    Coordinate coords = currentPlayer.selectCell();
    // Execute the move
    model.playTurn(coords.getRow(), coords.getColumn(), cardIndex);
    model.endTurn();
  } catch (IllegalArgumentException | IllegalStateException e) {
    System.out.println("Invalid move: " + e.getMessage());
    // Retry the move
  }
}
```

## Analysis

1. The Player interface is focused solely on decision-making, while the model handles game logic and state management. The interface supports both human and AI implementations without modifying the core game logic. Invalid moves are caught and can be handled appropriately, allowing for retry attempts. This interface makes it easy to create mock players for testing game logic.  

2. And the design allows for:
   - AI Player implementations
   - Remote human players
   - Live human players w/ the GUI
