# OOD Project 2 | Three Trios

## Homework 5:

## Overview

Three Trios is a two-player strategy game inspired by the classic card game Triple Triad we were required to implement for Homework 5 and will later extend with additional rules in future homeworks. The game is played on a customizable grid where players take turns placing cards with varying attack values. Each card has four sides (North, South, East, West) with associated attack strengths. The objective is to dominate the grid by controlling the majority of the cards through strategic placements and battles.

This project implements the Three Trios game with a robust Model-View-Controller architecture, designed for extensibility, allowing for future enhancements such as variant rules and AI players. The game supports reading configurations from files, enabling customizable grid layouts and card decks. While the current implementation includes a textual view for game state visualization, we will later extend this to include a graphical user interface (GUI) in future homeworks.

## Quick Start

Below is a simple example demonstrating how to set up and start a game of Three Trios using the provided model and readers.

1. Import the project into your IDE (IntelliJ recommended).
2. Create a new Java file and import the following packages:

```java
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.model.card.ThreeTriosCard;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.ClassicalThreeTriosModel;
import cs3500.threetrios.view.GridFileReader;
import cs3500.threetrios.view.DeckFileReader;
```

3. Use the File.separator to create the path to the config files for the grid and deck in the docs folder that works for any operating system:

```java
String gridWithNoReachableCardCells = "docs" + File.separator + "boards" + File.separator + "boardWithNoUnreachableCardCells.config";
String deckWithSomeCardsMissing = "docs" + File.separator + "cards" + File.separator + "someCards.config";
```

4. Create a new ThreeTriosBoard and a new deck of cards using the provided readers:

```java
ThreeTriosBoard board = new ThreeTriosBoard(
    new GridFileReader().readFile(gridWithNoReachableCardCells)
);
List<CustomCard> deck = new DeckFileReader().readFile(deckWithSomeCardsMissing);
```

5. Create a new ClassicalThreeTriosModel with the boolean parameter set to true to enable shuffling the deck and start the game with the deck and board:

```java
ClassicalThreeTriosModel model = new ClassicalThreeTriosModel(true);
model.startGame(board, deck);
```
6. Use the methods in the ThreeTriosModelInterface to interact with the model and play the game.


## Key Components & Subcomponents

### Detailed Breakdown

- **`model.card`**
  - Defines the `CustomCard` interface and its implementation `ThreeTriosCard`, along with enums for `AttackValue`, `CardColor`, and `PlayerColor`.

- **`model.cell`**
  - Contains the `Cell` interface, its implementation `ThreeTriosCell`, and the `CellState` enum to represent the state of each cell.

- **`model.grid`**
  - Implements the `Grid` interface through the `ThreeTriosBoard` class, managing the placement and retrieval of cards on the grid.

- **`model.rules`**
  - Manages game rules via the `RuleKeeper` interface and its implementations, including `GameRules` and `BasicThreeTriosGame`.

- **`controller.readers`**
  - Provides functionality to read grid and deck configurations from files through `GridFileReader` and `DeckFileReader`.

- **`view`**
  - Implements the `TextualView` interface with `ThreeTriosTextualView`, offering a console-based representation of the game state.

### Model
The core of the game logic is encapsulated within the model. It manages the state of the game, including the grid, players' hands, and the rules governing gameplay.

- **`ThreeTriosModelInterface`**
  - Defines the contract for the game model, outlining essential operations such as starting the game, playing turns, and determining the game's outcome.

- **`BaseThreeTriosModel`**
  - An abstract class providing common implementations for different game variants, facilitating future extensions with additional rules.

- **`ClassicalThreeTriosModel` & `ReadOnlyThreeTriosModel`**
  - Implements the classic rules of Three Trios, handling game flow, move validations, and score calculations; the version of the game we were asked to implement for this homework. The readonly version is used to allow the controller and view to interact with the model without modifying its state. It is used the textual view currently.

- **`GameState`**
  - An enum representing the possible states of the game used to transition between different phases of the game in the model.

- **`PlayerColor`**
  - An enum representing the possible colors of a player; same as the color of the cards they control, but CardColor has an UNASSIGNED value to represent cards that do not have a player color yet, whereas PlayerColor only has RED and BLUE values. 

### Rules

- **`RuleKeeper`**
  - An interface defining the rules of the game, ensuring that moves are legal and handling battle mechanics between cards.

- **`GameRules`**
  - An abstract class implementing `RuleKeeper`, managing the common implementations of the rules of the game, ensuring that moves are legal and handling battle mechanics between cards.

- **`BasicThreeTriosGame`**
  - A concrete implementation of `GameRules`, managing the basic game flow, including turn management and game state transitions for the classic version of the game.

- **`Coordinates`**
  - A helper class representing positions on the grid, used during battle phases.

### Cards
Cards are fundamental to gameplay, each possessing unique attack values on their four sides.

- **`CustomCard`**
  - An interface representing a card with a name, attack values, and ownership color.

- **`ThreeTriosCard`**
  - A concrete implementation of `CustomCard`, storing attack strengths and managing color changes upon battles.

- **`AttackValue`, `CardColor` & `Direction`**
  - Enums defining possible attack strengths, colors of a card and the four cardinal directions which are used to instantiate a `ThreeTriosCard` object.

### Grid
The grid defines the playing field where cards are placed and battles occur.

- **`Grid`**
  - An interface representing the game board, providing methods to interact with cells, place cards, and retrieve adjacent cards.

- **`ThreeTriosBoard`**
  - Implements the `Grid` interface, managing the state of each cell and enforcing grid-related invariants.

### Cells
Cells are the individual units within the grid, which can either be holes or contain cards.

- **`Cell` & `ThreeTriosCell`**
  - Interfaces and concrete classes representing individual cells, handling card placements and state transitions.

- **`CellState`**
  - An enum representing the possible states of a cell (RED, BLUE, HOLE, EMPTY).

### View
The view component is responsible for rendering the game state to the user.

- **`TextualView` & `ThreeTriosTextualView`**
  - Interface and implementation for a console-based view, providing textual representations of the game state.

### Configuration Readers (in the Controller package)
These classes facilitate loading game configurations from external files.

- **`ConfigurationFileReader`**
  - A generic interface defining the contract for reading configuration files.

- **`DeckFileReader` & `GridFileReader`**
  - Concrete implementations for reading card decks and grid configurations from files, respectively.

## Source Organization

The following diagram shows the source organization of the project:

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
|------ TextualView.java
|------ ThreeTriosTextualView.java
|||||- model/
|------ BaseThreeTriosModel.java
|------ ClassicalThreeTriosModel.java
|------ ReadOnlyThreeTriosModel.java
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
```
#### subdirectories III | test:
NOTE: Has the same structure as the src directory but with test classes for each of the classes 
in the src directory that are relevant to testing for the purpose of a working implementation 
for the model running the game.
