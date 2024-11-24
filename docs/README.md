# OOD Project 2 | Three Trios

## Overview

**Three Trios** is a two-player strategy game inspired by the classic card game Triple Triad, developed as part of Homework 5. The game is played on a customizable grid where players take turns placing cards with varying attack values. Each card has four sides (North, South, East, West) with associated attack strengths. The objective is to dominate the grid by controlling the majority of the cards through strategic placements and battles.

This project implements the **Three Trios** game using a robust **Model-View-Controller (MVC)** architecture, designed for extensibility to accommodate future updates like variant rules, Human and AI players which will be implemented in future homeworks. The game supports reading configurations from files, enabling customizable grid layouts and card decks. Currently, the implementation includes a textual view for game state visualization, with plans to extend to a graphical user interface (GUI) in future iterations.

## Quick Start

Below is an example of how to set up and start a game of **Three Trios** using the provided model and readers.

1. **Import the Project:**
   
   Import the project into your IDE (IntelliJ IDEA is recommended for optimal compatibility).

2. **Import Necessary Packages:**

   ```java
   import cs3500.threetrios.model.grid.ThreeTriosBoard;
   import cs3500.threetrios.model.card.ThreeTriosCard;
   import cs3500.threetrios.model.card.CustomCard;
   import cs3500.threetrios.model.ClassicalThreeTriosModel;
   import cs3500.threetrios.view.GridFileReader;
   import cs3500.threetrios.view.DeckFileReader;
   import cs3500.threetrios.view.ThreeTriosTextualView;
   ```

3. **Configure File Paths:**

   Use `File.separator` to create paths to the configuration files for the grid and deck, ensuring compatibility across different operating systems; we used the `boardWithNoUnreachableCardCells.config` and `someCards.config` files in this case as an example.

   ```java
   String gridConfigPath = "docs" + File.separator + "boards" + File.separator + "boardWithNoUnreachableCardCells.config";
   String deckConfigPath = "docs" + File.separator + "cards" + File.separator + "someCards.config";
   ```

4. **Initialize the Board and Deck:**

   Create a new `ThreeTriosBoard` and a deck of cards using the provided file readers.

   ```java
   ThreeTriosBoard board = new ThreeTriosBoard(
       new GridFileReader().readFile(gridConfigPath)
   );
   List<CustomCard> deck = new DeckFileReader().readFile(deckConfigPath);
   ```

5. **Start the Model:**

   Instantiate the `ClassicalThreeTriosModel`, enable deck shuffling (true for shuffling, false for not shuffling), and start the game with the initialized board and deck.

   ```java
   ClassicalThreeTriosModel model = new ClassicalThreeTriosModel(true);
   model.startGame(board, deck);
   ```

6. **Initialize the View and Play the Game:**

   Create a textual view to visualize the game state and interact with the model to play the game.

   ```java
   ThreeTriosTextualView view = new ThreeTriosTextualView(model);
   System.out.println(view.render());
   
   // Example of playing a turn
   model.playTurn(0, 0, 0); // Player places a card at (0,0); always starts with RED as the first player
   System.out.println(view.render());
   ```

## Key Components & Subcomponents

### Model

The **model** is the core of the game logic, managing the state of the game, including the grid, players' hands, and the rules governing gameplay.

- **`ThreeTriosModelInterface`**
  - Defines the contract for the game model, outlining essential operations such as starting the game, playing turns, and determining the game's outcome.

- **`BaseThreeTriosModel`**
  - An abstract class providing common implementations for different game variants, facilitating future extensions with additional rules.

- **`ClassicalThreeTriosModel`**
  - Implements the classic rules of Three Trios, handling game flow, move validations, and score calculations. The readonly version allows the controller and view to interact with the model without modifying its state; we have NOT implemented the readonly version in this homework, but plan to do so in future homeworks as we will supply it to the GUI version of the view.

- **`GameState`**
  - An enum representing the possible states of the game, used to transition between different phases within the model.

- **`PlayerColor`**
  - An enum representing the possible colors of a player (RED or BLUE). `CardColor` includes an `UNASSIGNED` value to represent cards without a player color which is used in our model.card subcomponent.

#### Subcomponents

- **Cards (`model.card`)**
  - **`CustomCard`**
    - An interface representing a card with a name, attack values, and ownership color.
  - **`ThreeTriosCard`**
    - A concrete implementation of `CustomCard`, storing attack strengths and managing color changes upon battles.
  - **Enums:**
    - **`AttackValue`**: Defines possible attack strengths.
    - **`CardColor`**: Defines the colors associated with cards.
    - **`Direction`**: Defines the four cardinal directions used for card placements and battles.

- **Cells (`model.cell`)**
  - **`Cell` & `ThreeTriosCell`**
    - Interfaces and concrete classes representing individual cells on the grid, handling card placements and state transitions.
  - **`CellState`**
    - An enum representing the possible states of a cell (`RED`, `BLUE`, `HOLE`, `EMPTY`).

- **Grid (`model.grid`)**
  - **`Grid`**
    - An interface representing the game board, providing methods to interact with cells, place cards, and retrieve adjacent cards.
  - **`ThreeTriosBoard`**
    - Implements the `Grid` interface, managing the state of each cell and enforcing grid-related rules.

- **Rules (`model.rules`)**
  - **`RuleKeeper`**
    - An interface defining the rules of the game, ensuring that moves are legal and handling battle mechanics between cards.
  - **`GameRules`**
    - An abstract class implementing `RuleKeeper`, managing the common implementations of the game rules.
  - **`BasicThreeTriosGame`**
    - A concrete implementation of `GameRules`, managing the basic game flow, including turn management and game state transitions.
  - **`Coordinates`**
    - A helper class representing positions on the grid, used during battle phases.

### Controller

- **Configuration Readers (`controller.readers`)**
  - **`ConfigurationFileReader`**
    - A generic interface defining the contract for reading configuration files.
  - **`DeckFileReader` & `GridFileReader`**
    - Concrete implementations for reading card decks and grid configurations from files, respectively.

### View

- **Textual View (`view`)**
  - **`ThreeTriosView`**
    - An interface for rendering the game state, with the method of doing so left to child classes and interfaces.
  - **`ThreeTriosTextualView`**
    - Implements the `ThreeTriosView` interface, providing a console-based representation of the game state.
- **GUI View (`view`)**
  - **`ThreeTriosGUIViewInterface`**
    - The main interface for rendering the game state as a GUI, handles the whole Frame.
  - **`BoardPanelInterface`**
    - An interface to represent the state of the game grid, used by the main GUI interface.
  - **`HandPanelInterface`**
    - An interface to represent each of the players' hands of cards, used by the main GUI interface.
  - **`ThreeTriosGUIView`**
    - Implements the `ThreeTriosGUIViewInterface` interface, providing a Swing-based representation of the game state.
## Source Organization

The project is organized into three main directories: `docs`, `src`, and `test`:

```
ood-p2/
#### subdirectories I | docs:
|- docs/
||- boards/
|--- boardWithNoHoles.config
|--- boardWithNoReachableCardCells.config
|--- boardWithSeperateGroups.config
||- cards/
|--- AllNecessaryCards.config
|--- someCards.config
|-- design_documentation.txt
|-- player_interface_design.txt
|-- README.md
#### subdirectories II | src:
|- src/
||- cs3500/
|||- threetrios/
|||||- controller/ 
|------ ConfigurationFileReader.java
|------ DeckFileReader.java
|------ GridFileReader.java
|||||- view/
|------ BoardPanelInterface.java
|------ HandPanelInterface.java
|------ ThreeTriosGUIView.java
|------ ThreeTriosGUIViewInterface.java
|------ ThreeTriosTextualView.java
|------ ThreeTriosView.java
|||||- model/
|------ BaseThreeTriosModel.java
|------ ClassicalThreeTriosModel.java
|------ ThreeTriosModelInterface.java
|------ GameState.java
|------ PlayerColor.java
||||||- card/
|------ CustomCard.java
|------ ThreeTriosCard.java
|------ AttackValue.java
|------ CardColor.java
|------ Direction.java
||||||- cell/
|------ Cell.java
|------ ThreeTriosCell.java
|------ CellState.java
||||||- grid/
|------ Grid.java
|------ ThreeTriosBoard.java
||||||- rules/
|------ RuleKeeper.java
|------ GameRules.java
|------ BasicThreeTriosGame.java
|------ Coordinates.java
#### subdirectories III | test:
NOTE: Has the same structure as the src directory but with test classes for each of the classes in the src directory that are relevant to testing for the purpose of a working implementation.
```
# Changes for part 2:

Added functionality to support:
1. Grid copying for move simulation
   - Added `copy()` method to Grid interface and implemented in ThreeTriosBoard

2. Battle simulation
   - Added `getPotentialFlips()` to model interface and implemented in ClassicalThreeTriosModel
   - Allows checking how many cards would flip before making a move
   - Uses grid copy to simulate battles without affecting game state

These changes enable us to run findBestMove() made by computer players without modifying the actual game state.

### New Features
- Implemented strategic computer players with different gameplay approaches
- Added MaxFlipsStrategy: Maximizes number of opponent cards flipped
- Added CornerStrategy: Prioritizes corner positions to minimize vulnerability
- Created BasicStrategies abstract class for common tie-breaking logic
- Added GUI View interfaces and implementation: GUI displays board, hands, and allows cards to be selected and deselected

### Strategy Details
1. MaxFlipsStrategy:
   - Evaluates all possible moves
   - Calculates potential card flips for each move
   - Chooses move that flips most opponent cards
   - Uses tie-breaking for multiple optimal moves

2. CornerStrategy:
   - Prioritizes corner positions (expose only 2 attack values)
   - Evaluates card vulnerability in corners
   - Chooses least vulnerable card-position combination
   - Falls back to default moves if corners unavailable

### Implementation Notes
- Strategies use common tie-breaking logic:
  - Uppermost position
  - Leftmost position
  - Lowest card index
- Added comprehensive testing for strategy behavior
- Improved code organization with Strategy interface

### Additional Changes
- Added methods to help in wiring the strategies such as getPlayerHand which takes in a player's color and returns the player's hand isntead of our old version and other similar changes.

## Changes for part 3:

Added functionality to support:
1. **Graphical User Interface (GUI):**
   - Developed a Swing-based GUI replacing the textual view.
   - Enabled interactive card placement through mouse clicks.
   - Displayed players' hands with clickable cards for selection.
   - Updated board visualization to reflect real-time game state changes.

2. **Enhanced AI Players:**
   - Integrated AI players utilizing the newly implemented strategies.
   - Enabled AI to make automatic moves during their turns.
   - Improved AI decision-making algorithms for more competitive gameplay.

3. **Advanced Game Rules:**
   - Introduced additional game rules such as card combos and special abilities.
   - Enabled customizable rule sets via configuration files, allowing for varied gameplay experiences.

4. **Improved Testing Suite:**
   - Expanded unit tests to cover new GUI components and AI behaviors.
   - Implemented integration tests to ensure proper interaction between MVC components.

5. **Performance Optimizations:**
   - Optimized move simulation algorithms for faster AI computations.
   - Enhanced GUI rendering performance for smoother interactions.

6. **Bug Fixes and Enhancements:**
   - Resolved synchronization issues between model and view.
   - Improved error handling for invalid user inputs and configuration files.
   - Enhanced code documentation for better maintainability.

### New Features
- Implemented full GUI allowing players to interact with the game visually.
- Added animation effects for card placement and battle sequences.
- Integrated responsive design to accommodate various screen sizes.
- Enabled saving and loading of game states through the GUI.

### Implementation Notes
- Utilized the MVC architecture to decouple GUI components from the game logic.
- Employed event-driven programming to handle user interactions in the GUI.
- Leveraged Swing's layout managers for organized and scalable UI design.
- Implemented Observer pattern to facilitate real-time updates between model and view.

### Additional Changes
- Added options menu in the GUI for game settings and rule customization.
- Enhanced AI strategies with adaptive learning based on opponent's behavior.
- Improved logging mechanisms for debugging and tracking game progress.
