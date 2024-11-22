# Three Trios Game

### Overview

Three Trios is a two-player card game developed in Java, inspired by the classic Triple Triad.
Players place uniquely named cards with distinct attack values on a customizable grid. The objective
is to control more cards than your opponent by strategically flipping the opponent’s cards through
battles based on attack values. The game supports various grid configurations and card sets,
allowing
for flexible and engaging gameplay.

### Quick Start

Here's a simple example to get started with the Three Trios game:

```java
@Test
public void playFullGame(){
        // CXX
        // CXC
        // XCC
        model.startGame(smallDeck,3,3,twoCardCellGroups);
        // CXX
        // CXR
        // XCC
        model.playToCell(1,2,0); // 7 3 9 A
        // CXX
        // CXB
        // XCB
        model.playToCell(2,2,0); // A 2 4 4, A > 3, (2,1) flips
        // RXX
        // CXB
        // XCB
        model.playToCell(0,0,0); // 2 8 9 9
        // RXX
        // CXB
        // XBB
        model.playToCell(2,1,0); // 8 3 5 7
        // RXX
        // RXB
        // XBB
        model.playToCell(1,0,0); // 7 2 5 3
        // Red has 2 cards, Blue has 4 cards (3 on grid, 1 in hand).
        Assert.assertEquals("Player Blue is the winner",PlayerColor.BLUE,model.getWinner());
        }
```

### Key Components

#### Model

- The Model manages the game state, including the grid, players' hands, and game rules.
- It handles all game logic, such as card placement, battles, and determining the game outcome.
- **Classes and Interfaces:**
- `ThreeTriosModel`: Interface defining the game's core behaviors and state observations.
- `Card`: Interface defining behaviors for a card in the game, which has a unique name and 4 attack
  values.
- `AttackValue`, `CellType`, `PlayerColor`: Enumerations defining attack values, cell types, and
  player colors respectively.

#### View

- The View is responsible for presenting the game state to the user.
- The current implementation provides a textual representation of the game, facilitating easy
  testing and debugging, as well as a GUI view.
- **Classes and Interfaces:**
- `ThreeTriosGraphicalView` : Interface for a GUI view of a Three Trios game.
- `ThreeTriosGraphicalViewImplementation` : Concrete implementation of `ThreeTriosGraphicalView`.
- `ThreeTriosView`: Interface defining behaviors for a text based view.
- `ThreeTriosTextView`: Concrete implementation of `ThreeTriosView`.

#### Controller

- The Controller communicates between the model and the view, acting as an intermediary.
- **Classes and Interfaces:**
- `ConfigReader` : Interface defining behaviors for objects that read text-based configuration
  files.
- `Features` : Interface defining controller actions between the controller and the view.
- `ModelFeatures` : Interface defining controller actions between the controller and the model.
- `Player` : Interface defining player actions in a game.
- `ThreeTriosController` : Implementation of a controller for a Three Trios game.

#### Strategy

- Strategies for Three Trios choose a card to play and where to play them on the board for a given
  player.
- **Classes and Interfaces:**
- `Move` : Contains relevant information needed for a player to play a card to the board.
- `ThreeTriosStrategy` : Interface defining behaviors for a strategy.

#### Main Method

- The main method runs a game of Three Trios. It currently instantiates a model and a GUI view and
  starts the view.
- `ThreeTrios` : Contains the main method.

### Key Subcomponents

#### Model Subcomponents

- **ThreeTriosModelImplementation:**
- *Grid Management*: Maintains a 2D array of `Cell` objects representing the game grid.
- *Deck and Hands*: Manages the deck of cards and each player's hand.
- *Turn and Game Status*: Tracks the current player and the status of the game (NotStarted, Playing,
  GameOver).
- *Battle Mechanics*: Implements the logic for card battles and flipping opponent's cards based on
  attack values.
- *Configuration Files*: Can start the game given parameters for the game or through text files that
  follow the proper format.
- **ThreeTriosCard:**
- *Attributes*: Each card has a unique name and four attack values corresponding to North, South,
  East, and West.
- *Immutability*: Card attributes are immutable once created, ensuring consistent behavior during
  gameplay.
- **Cell:**
- *Cell Type*: Determines whether a cell is a hole or a card cell.
- *Card Ownership*: Tracks which player owns the card placed in the cell.

#### View Subcomponents

- **ThreeTriosGraphicalView:**
- *Graphical Rendering*: Converts the model's state into a graphical representation.
- *Selecting Cards*: Clicking on a card highlights the card.
- *Printing to Console*: Clicking on the grid or a card in the hand prints details to the console.
- **ThreeTriosTextView:**
- *Rendering Logic*: Converts the model's state into a readable text format.
- *Output Destination*: Can direct output to any `Appendable` object, such as `System.out` or
  a `StringBuilder`.

#### Controller Subcomponents

- **ConfigReader:**
- *Read Files*: Extracts relevant game information from a file path.
- **ThreeTriosController**
- *Features*: Communicates between the game model and the game view and delegates tasks.
- *Notifications*: Informs the player when it is their turn and tells the player if they perform an
  invalid action.

### Source Organization

The source code is organized into the following packages:

- **cs3500.threetrios.model**

- *Enums*:
    - `AttackValue`: Defines possible attack values (1-9, A).
    - `CellType`: Defines cell types (HOLE, CARDCELL).
    - `PlayerColor`: Defines player colors (RED, BLUE).


- *Classes and Interfaces*:
    - `Card`: Interface for card behaviors.
    - `ThreeTriosCard`: Implementation of the `Card` interface.
    - `Cell`: Represents a grid cell.
    - `ReadonlyThreeTriosModel`: Interface defining observational behaviors from the model.
    - `ThreeTriosModel`: Interface defining mutating behaviors from the model.
    - `ThreeTriosModelImplementation`: Implementation of the `ThreeTriosModel` interface.


- **cs3500.threetrios.view**

- *Interfaces and Classes*:
    - `GridPanel`: Panel that displays the game board.
    - `HandPanel`: Panel that displays a player's hand.
    - `ThreeTriosGraphicalView`: Interface for a GUI view for the game.
    - `ThreeTriosGraphicalViewImplementation`: Implementation of `ThreeTriosGraphicalView`.
    - `ThreeTriosView`: Interface defining behaviors for a text based view.
    - `ThreeTriosTextView`: Implementation of the `ThreeTriosView` interface.


- **cs3500.threetrios.controller**

- *Interfaces and Classes*:
    - `Features`: Interface representing the features between the controller and the view.
    - `ModelFeatures`: Interface representing the features between the controller and the model.
    - `ThreeTriosController`: Implementation of `Features` and `ModelFeatures` to create a
      controller for the game.
    - `Player`: Interface defining player actions in a game.
    - `ComputerPlayer`: Implementation of `Player` for a computer player and emits player actions
      instead of a view.
    - `HumanPlayer`: Implementation of `Player` for a human player and does not emit player actions.
    - `ConfigReader`: Interface for objects that read text-based configuration files.
    - `DeckConfigReader`: Implementation of `ConfigReader` for reading files for a game’s card
      database.
    - `GridConfigReader`: Implementation of `ConfigReader` for reading files for a game’s grid.
    - `AbstractConfigReader`: Implementation of methods shared by `DeckConfigReader`
      and `GridConfigReader`.


- **cs3500.threetrios.strategy**

- *Interfaces and Classes*:
    - `ThreeTriosStrategy`: Interface defining behaviors for a strategy.
    - `Move`: Stores relevant information needed for a player to play a card to the board.
    - `FlipMostCards`: Strategy that finds the move that flips the most cards on the board.
    - `GoForTheCorners`: Strategy that finds the move where a card played in a corner of the board
      is hardest to flip.
    - `AbstractStrategyTiebreaker`: Implementation of shared tie-breaking behavior
      in `FlipMostCards`.
      and `GoForTheCorners`.


- *Test Classes (`cs3500.threetrios.tests`)*:
    - `cs3500.threetrios.model`
        - `CellTest`: Tests for the `Cell` class.
        - `ThreeTriosCardTest`: Tests for the `ThreeTriosCard` class.

    - `cs3500.threetrios.view`
        - `ThreeTriosTextViewTest`: Tests for the `ThreeTriosTextView` class.

    - `cs3500.threetrios.controller`
        - `DeckConfigReaderTest`: Tests for the `DeckConfigReader` class.
        - `GridConfigReaderTest`: Tests for the `GridConfigReader` class.
        - `MockComputerPlayerLog`: Mock computer player that records actions taken.
        - `MockModelGameWon`: Mock model where Player Red wins the game 5-3.
        - `MockModelRedToPlay`: Mock model where it is Player Red's turn.
        - `MockViewRecordActions`: Mock view that records actions taken.
        - `ThreeTriosControllerTest`: Tests for the `ThreeTriosController` class.

    - `cs3500.threetrios.strategy`
        - `FlipMostCardsTest`: Test for the `FlipMostCards` class.
        - `GoForTheCornersTest`: Test for the `GoForTheCorners` class.
        - `MockModelNoCornersOpen`: Mock model where the corners cannot be legally played to.
        - `MockModelNoPlayableCells`: Mock model where no cells can be legally played to.
        - `MockModelOneValidMove`: Mock model where only one move is valid.
        - `MockModelRecordActions`: Mock model that records actions, such as checking the legality of a move.
        - `MockModelStrategyTranscript`: Mock model that creates a strategy transcript for a 3x3 board.
          with no holes.

    - `ThreeTriosModelTest`: Tests for the `ThreeTriosModelImplementation` class.


- *Card Database Configuration Files (`deckConfigFiles`)*:
    - `badConfigs`: Configuration files that do not follow the proper format for card database
      configuration files. Used for testing purposes.
    - `bigDeck.txt`: A sample card database that contains 16 cards.
    - `smallDeck.txt`: A sample card database that contains 6 cards.

### Grid Config Files

- *Grid Configuration Files*:
    - `badConfigs`: Configuration files that do not follow the proper format for grid configuration
      files. Used for testing purposes.
    - `3x3AllCardCells.txt`: A sample grid with 3 rows and 3 columns where every cell is a card
      cell.
    - `3x3AllCardCellsTouch.txt`: A sample grid with 3 rows and 3 columns where all card cells can
      reach each other.
    - `3x3TwoCardCellGroups.txt`: A sample grid with 3 rows and 3 columns with two groups of card
      cells that cannot reach each other.
    - `5x3AllCardCells.txt`: A sample grid with 3 rows and 5 columns where every cell is a card
      cell.

### Configuration Files

**Grid Configuration File**

- Defines the grid layout for the game. The first line specifies the number of rows and columns.
- Subsequent lines represent each row of the grid using C for card cells and X for holes.
- Any lines following the last row of the grid are ignored.

```text
3 5
CCCCC
CCCCC
CCCCC
````

**Card Database File**

- Lists all available cards with their names and attack values in the order of North, South, East,
  and West.

```text
CorruptKing 7 3 9 A
AngryDragon 2 8 9 9
WindBird 7 2 5 3
HeroKnight A 2 4 4
WorldDragon 8 3 5 7
SkyWhale 4 5 9 9
```

### Changes for part 2

**Read-Only Model**

- We did not consider that the view should not be able to mutate a model in part 1.
- To solve this problem, we refactored `ThreeTriosModel` into two
  interfaces: `ReadonlyThreeTriosModel` and `ThreeTriosModel`.
- `ThreeTriosModel` contains observation methods of the model, while `ThreeTriosModel` contains
  methods that mutate the model.

**Added Functionality**

- Added a method that determines whether it is legal for the current player to play to the cell at
  the given coordinates (useful for strategy implementation)
- Added a method that returns the number of cards that would be flipped by playing a card to the
  cell at the given coordinates (useful for strategy implementation)
- Added a method that returns the score of a player (the number of cards owned on the grid plus the
  number of cards owned in the hand)

**Other Refactoring**

- Refactored implementations to use interface types instead of concrete classes when possible
- Moved classes `AbstractConfigReader`, `ConfigReader`, `DeckConfigReader`, and `GridConfigReader`
  and their associated test classes to a new package `cs3500.threetrios.controller`, as they handle
  I/O operations and should not belong to the model.

### Changes for part 3

**Model Notifications**

- Augmented `ThreeTriosModelImplementation` so that `ThreeTriosController` can register itself as a
  listener for events coming from the model.
- This allows the model to send various notifications to the controller.