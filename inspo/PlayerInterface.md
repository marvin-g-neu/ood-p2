Player Interface Design for Three Trios

---

Overview

The `Player` interface allows interaction between human and AI players with our current Three Trios
game model. It uses methods for initializing the player, determining and executing moves, getting
updates about the game state, and handling the end of the game.

---

Interface Overview:

1. Initialization

    - Method: `initialize(PlayerColor color, List<ThreeTriosCard> initialHand)`

    - Description: Sets up the player with their designated color and initial set of cards.

    - Parameters:
        - `PlayerColor color`: The player's assigned color (`RED` or `BLUE`).
        - `List<ThreeTriosCard> initialHand`: The initial collection of cards dealt to the player at
          the start of the game.

2. Deciding and Executing a Move

    - Method: `makeMove(ThreeTriosModel model)`

    - Description: Determines the player's next move based on the current game state and executes it
      by interacting directly with the game model.

    - Parameters:
        - `ThreeTriosModel model`: The current state of the game, providing access to the grid,
          hands, and other relevant information necessary for decision-making.

3. Receiving Game State Updates

    - Method: `updateGameState(ThreeTriosModel model)`

    - Description: Provides the player with the latest game state after each move, enabling them to
      adjust their strategy accordingly.

    - Parameters:
        - `ThreeTriosModel model`: The updated game model reflecting the latest changes in the game
          state, such as card placements and ownerships.

4. Handling Game Conclusion

    - Method: `onGameOver(PlayerColor winner)`

    - Description: Notifies the player about the end of the game and declares the winner.

    - Parameters:
        - `PlayerColor winner`: The color of the winning player (`RED` or `BLUE`), or `null` in the
          event of a tie.

---


Design Overview

- Interaction with the Model: By passing the `ThreeTriosModel` to methods like `makeMove`
  and `updateGameState`, players can access all necessary information about the game state without
  relying on intermediary classes.

- Clear Separation of Concerns: Each method in the interface has a distinct responsibility, whether
  it's initializing the player, making a move, updating the game state, or handling the game's end.

- Support for Both Human and AI Players: The interface is designed to be versatile, allowing for
  different implementations tailored to human interactions (e.g., GUI-based inputs) or AI
  decision-making algorithms.

---