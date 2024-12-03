For all the new changes check the [Patch Notes](#12-homework-7).

A demo can also be found at [this link](https://www.youtube.com/playlist?list=PL_mWypzIdJnFPDLdzXjvfGlL6d42LxgEQ)
# Table of Contents
- [Table of Contents](#table-of-contents)
- [Overview of the Model](#overview-of-the-model)
  - [Overview of the Cells](#overview-of-the-cells)
  - [Overview of the Players](#overview-of-the-players)
    - [Overview of the AI](#overview-of-the-ai)
  - [Overview of the Board](#overview-of-the-board)
  - [Overview of the Builder](#overview-of-the-builder)
    - [Cell File](#cell-file)
    - [Board File](#board-file)
    - [Adding Players](#adding-players)
- [Overview of Text Implementation](#overview-of-text-implementation)
  - [Overview of the Text View](#overview-of-the-text-view)
  - [Overview of the Text Controller](#overview-of-the-text-controller)
  - [Starting the Text Implementation](#starting-the-text-implementation)
- [Overview of GUI Implementation](#overview-of-gui-implementation)
  - [Overview of the GUI View](#overview-of-the-gui-view)
    - [Homework 7 Implementation Changes](#homework-7-implementation-changes)
      - [Small Changes](#small-changes)
  - [Overview of the GUI Controller](#overview-of-the-gui-controller)
    - [Clicks on the cells in the hand](#clicks-on-the-cells-in-the-hand)
    - [Clicks on the board](#clicks-on-the-board)
    - [Homework 7 Implementation Changes](#homework-7-implementation-changes-1)
  - [Starting the GUI Implementation](#starting-the-gui-implementation)
    - [Homework 6](#homework-6)
    - [Homework 7](#homework-7)
    - [Disclaimer For Future Project](#disclaimer-for-future-project)
      - [Update Disclaimer as of Project 7](#update-disclaimer-as-of-project-7)
- [Patch Notes](#patch-notes)
  - [1.1 (Homework 6)](#11-homework-6)
    - [Everything new](#everything-new)
    - [Small Changes](#small-changes-1)
  - [1.2 (Homework 7)](#12-homework-7)
    - [Everything new](#everything-new-1)
    - [Small Changes](#small-changes-2)
  
# Overview of the Model

Our ThreeTrioGameModel creates n-players to play the game.
Determined by how many colors in the ThreeTrioColor enum class. 
Players can go in any order by using the playCell method in the ThreeTrioPlayer class.
The model also has a way to get the player associated with the color so that the user can actually play a cell.


## Overview of the Cells

Cells are configured using the following values

* **String name**: to be the name of the cell
* **int northValue**: to be the northern value of the cell.
* **int southValue**: to be the southern value of the cell.
* **int eastValue**: to be the eastern value of the cell.
* **int westValue**: to be the western value of the cell.
* **ThreeTrioColor color**: to be the color of the cell to associate it with a player.

where all the in value are between 0 and 10.

When viewing a ThreeTrioCell Object the following is a documentation of its setup and values
A simple toString() output of a cell is as follows where the value 10 is replaced with A:

````
<name> <northValue> <southValue> <eastValue> <westValue>
````

To create a cell object the same format is used in a given file.
This is further clarified in the overview of the model, under the builder section.

## Overview of the Players

The ThreeTrioPlayer class is used to store values associated with a player.
This includes the hand and the color for the Player being referenced.
The color is of the ThreeTrioColor enum and accommodates any amount of colors added to it.
Currently, there are 52 ThreeTrioColors added, so the max number of players in a game is 52.
Moves are made by the player using the playCell method.


### Overview of the AI


The AI is based off of the StrategyEnum which declares the strategy that the AI should utilize
for the game. These Enums are then used in the ThreeTrioAIFactory to create an AI which employs
the given strategy. These are given as lists as should the user input several strategies the AI
will implement all the given strategies where the output of the first strategy becomes the input
of the second strategy and so on, create somewhat of a chain of strategies.
This Enum stores strategies, in this case:
 
* CAPTURE_STRAT(CaptureStrategy) - plays the move with the most captures
* CORNER_STRAT(CornerStrategy) - plays to only the corners
* DEFENSIVE_STRAT(DefensiveStrategy) - plays the cell that is the hardest to take
* RANDOM_STRAT(RandomStrategy) - plays a random cell at a random location

This means that each enum is related to the relevant strategy, however, as stated prior, should
they be in a list the AI will implement them both. 

We felt as though the defensive strategy of the AI encapsulates both the Min-Max
and defense strategy as they seem fundamentally the same.

IE: For a case where List.of(StrategyEnum.CAPTURE_STRAT, StrategyEnum.DEFENSIVE_STRAT, StrategyEnum.RANDOM_STRAT)

The created AI will consider all the strongest moves as determined by CaptureStrategy,
and out of all theses strongest moves it find the one that is the most defensive,
out of these moves it will choose a random move as they are all the same strength.

This is done using a PipeStrategy, which takes in two AI strategies and determines a best moves using
the logic from both. It does this by determining a list of good moves from strategy 1 and taking this
list and choosing a best move from it utilizing the logic of strategy 2. As an example, should an AI
be made from CAPTURE_STRAT and DEFENSIVE_STRAT the AI will determine a move by getting a list of moves
from the CAPTURE_STRAT and then determines the best move amongst them with the logic of an AI who 
implements DEFENSIVE_STRAT. 
IE: The defensive AI will normally play high sided cells with no regard to captures, however, with this
implementation the AI will focus on getting positions with high capture amounts, and then determine
which cells in its hand is the hardest to recapture while still maintaining the chain capture.

Each Strategy extends the AI abstract class and holds the logic the AI uses to 
perform moves. This logic will return a list of moves that are the strongest based on the strategy.
The implementation of all the strategies can be found in the threetrio.model.players.ai.strategies
package.


## Overview of the Board

The board is configured like this:
````
  ----X---> 
| ########## 
| ########## 
| ########## 
Y ##########
| ########## 
| ########## 
V ##########
````

where to get a position (x,y) on the board, you would use board[y][x].
The coordinates of the board start at (0,0), the top-left corner.
The board stores Cells and is a 2D array. 
Cells on our board can be represented in a few ways:

* **A Cell played by a player**: represented by an Object in the cell class
* **An empty square**: represented by a null
* **A Hole Cell**: represented by ThreeTrioCell.empty

For our implementation, we decided to store the cell in the corresponding (x,y).
For empty square of the board would be null,
and the holes in the board would be represented by our ThreeTrioCell.empty cell.


## Overview of the Builder

The ThreeTrioGameBuilder class is used to create instances of the ThreeTrioGameModel.
This is used to take in files and parse through them to create a viable deck and board for the
model to utilize in its gameplay.
All files used for parsing must be placed into the respective folders 
within the configs folder prior to running if you directly use the filename,
but if not you would have to give the absolute path of the files
or the relative path from where you ran it.

### Cell File

The cell file must be formatted like this:

````
<name> <int> <int> <int> <int>
````

An example file would look like:

````
cell1 1 1 1 1
cell2 2 2 2 2
cell3 3 3 3 3
````

The section under Overview of Cells clarifies the purpose of each value in this format.

````
<name> <northValue> <southValue> <eastValue> <westValue>
````


### Board File

The board file must be formatted like this:

````
<Number of Rows> <Number of Columns>
<Row 1>
<Row 2>
...
````

For the rows and columns, a hole marked X and a empty cell slot is marked by C.

An example file can be seen below:

````
5 5
CXCXX
CXCXC
CCCXX
CXCXC
CXCXC
````

This creates a board that looks like this:

````
# #  
# # #
###  
# # #
# # #
````

where "#" is a place where the user can play cells and "." is a hole.

### Adding Players

When a Player is created a Strategy must be specified,
the absence of one, or null, represents a Human Player.
Thus to create a Human Player the builder requires a null. 
The order which you add the players in the builder determines the order of play in the controller.
To add a player type to the game you can use builder.setupPlayers(), For example:


```
 ThreeTrioModel model = new ThreeTrioGameBuilder()
        .setGrid("chess.txt")
        .setCells("completeDeck.txt")
        .setupPlayers(ThreeTrioColor.RED,null)
        .setupPlayers(ThreeTrioColor.BLUE,null)
        .play(new ThreeTrioModel(),true);
```

This would create a model with 2 players where RED goes first and BLUE goes second.
To add an AI to the model you can set the second param of builder.setupPlayers() to a list of the AI's attributes:

```
 ThreeTrioModel model = new ThreeTrioGameBuilder()
        .setGrid("chess.txt")
        .setCells("completeDeck.txt")
        .setupPlayers(ThreeTrioColor.RED,List.of(StrategyEnum.CORNER_STRAT))
        .setupPlayers(ThreeTrioColor.BLUE,null)
        .play(new ThreeTrioModel(),true);
```

This would create a model with 2 players, one of which being an AI using the CornerStrategy. 
Even if the player already exist, this method can override the data in the player. For example

```
 ThreeTrioModel model = new ThreeTrioGameBuilder()
        .setGrid("chess.txt")
        .setCells("completeDeck.txt")
        .setupPlayers(ThreeTrioColor.RED,List.of(StrategyEnum.CORNER_STRAT))
        .setupPlayers(ThreeTrioColor.BLUE,null)
        .setupPlayers(ThreeTrioColor.RED,null)
        .play(new ThreeTrioModel(),true);
```

This method would create a model with only 2 player, despite the 3 call to setupPlayers, as
the ThreeTrioColor.RED got overwritten from the CornerStrategy to just being a normal player.

For the user's convince we also added a massSetPlayers method, which sets n amount of players to
a specific strategy. Unlike the previous this method can not overwrite data from existing players.
A use case would be:

```
ThreeTrioModel model = new ThreeTrioGameBuilder()
        .setGrid("chess.txt")
        .setCells("completeDeck.txt")
        .massSetPlayers(null, 10)
        .play(new ThreeTrioModel(),true);
```

This creates a model with 10 players. We can also chain the massSetPlayers together so:

```
ThreeTrioModel model = new ThreeTrioGameBuilder()
        .setGrid("chess.txt")
        .setCells("completeDeck.txt")
        .massSetPlayers(null, 10)
        .massSetPlayers(List.of(StrategyEnum.DEFENSIVE_STRAT),10)
        .play(new ThreeTrioModel(),true);
```

would create a model with 20 players, 10 of which are AI using the defensiveStrategy.

# Overview of Text Implementation

## Overview of the Text View

This view sends a string to an appendable something about the game state. <br>
It renders the board as "_", " ", "R", "B", or any other color characters. <br>
An example board would look like:

````
__B B
_R_ R
_ ___
````


where:

* "_" is empty slot
* " " is a hole
* "R" is the red player's cell
* "B" is the blue player's cell

It can also render a players hand, where it renders every cell in the hand with:

````
<name> <northValue> <southValue> <eastValue> <westValue>
````

followed by a new line. An example print of this would be:

````
cell1 1 2 3 4
cell2 5 6 7 8
cell3 9 A 1 2
````

## Overview of the Text Controller

The ThreeTrioTextController is used to create a textual view of the ThreeTrioGame.
This controls the model to create a game of ThreeTrio.
Important Parts of the Controller

* A readable and appendable are given to the controller class to be used.
* The readable is from where the inputs are received to the controller.
  * The handleInput method is used to get the required inputs from the user.
* The appendable is where the outputs of the controller are sent.
  * This is used to send messages to the user to inform them about the results of their
              inputs sent into the system.
* This class is also used to determine which player is currently taking their turn.

## Starting the Text Implementation

```java
public class Main{
  public static void main(String[] args){
    ThreeTrioModel model = new ThreeTrioGameBuilder().setCells("<insert deck file name>")
                .setGrid("<insert board file name>").play(new ThreeTrioGameModel(),true);
    ThreeTrioView view = new ThreeTrioGameTextView(System.out,model);
    ThreeTrioController controller =
            new ThreeTrioTextController(new InputStreamReader(System.in),System.out);
    controller.playGame(model,view);
  }
}
```

This is some example code to run the game with 2 people in the console.
Just replace &lt;insert deck file name&gt; with the name of your deck file and
replace &lt;insert board file name&gt; with the name of your board configuration file.


# Overview of GUI Implementation

## Overview of the GUI View

The GUI implementation of this game showcases hands of players on the edges of the Frame.
It renders the board as yellow and gray spaces with player hands on the sides. The active
player will always be on the left hand side of the screen,
if rotation is requested in the controller.

At the start of the game an example board would look like:

![start.png](images%2Fstart.png)


where:

* yellow spaces represent an empty slot
* Gray spaces represent a hole
* The left hand side is the active player's hand
* The colors on the board represent an instance of each player
* The title of the frame, telling you who's turn it is


The game allows for continuous play, where cells leave the hand when used.
For the ease of use of the user, when a cell is select it will have a thick black border around it.
At a middle section of the game it can be seen that due to players having completed moves.
Their hands decrease in size and the board can be seen to have a clear representation of 
those that are in the game and have cells still showcasing their color on the board. 
The view uses the information given by the board held in the model to depict this.
As seen in the below image:

![midgame.png](images%2Fmidgame.png)

In addition, by storing both AI and human Players the model is able to send the information
of their hands for depiction in the view. The also allows for the addition of rotation to the
view, which ensures that the active player is always shown on the left hand side and the next
player to take a turn is on the right hand side.
The game being used as an example was created with 1 Human Player: Blue and 25 AI players.
As seen in the following image, since the game board had less than 25 moves left after the 
human played, the game ended on an AI turn. This is why the colors change and showcase the AI
hands as well as the functionality of the rotation implementation.
In addition, the winner of the game at it's end is showcased as the title of the Frame in the 
top left corner of the window opened when running the game.
Here is the ending of the game for a showcase:


![finishedGame.png](images%2FfinishedGame.png)

### Homework 7 Implementation Changes

In this iteration we have changed it so that each Player has their own view associated with them.
This ensures that the player will always be showcased on the left side while the current player to play
in the game will be shown on the right, if the current player is the player on the view, then it shows the next player.
This means that in a game between RED, BLUE, and GREEN there will be 3 views created,
each view will depict the relevant player on the left side and RED on the right side,
except for RED which will have the next player's hand on the right side, which in this case is BLUE.
Once RED has played, every view will update to have BLUE showcased on the right side,
except for BLUE which will have the nextPlayer's hand on the right side, which in this case is GREEN.
Then once BLUE has played, the right side will be updated to GREEN on every view,
except for GREEN, which will have BLUE on the right side. This cycle happens repeatedly 
until the game is over, with the same pattern continuing regardless of the amount of players.


This new implementation also comes with popup panels for when an error is thrown to inform the user.
At the end of the game, it popups with a score break down telling the user the scores and placements
of all the players.

#### Small Changes

* Added 26 new ThreeTrioColors with unique display colors
* Added lighter color text on to darker backgrounds for more readability of the cards.
* Changed the storage of all the static variable and methods in the ThreeTrioGUIView to a class named ViewConstants (Makes it easier to customize the view of the game)
* Changed how refreshing works, removed board refresh when performing a cell selection on a handPanel
  * This increases speed as refreshes occur less, specifically boosting large board performance.

## Overview of the GUI Controller

The ThreeTrioGUIController receives model coordinates from the click on the view.
The controller then parses those inputs and communicates it with the model.
This controller also handles AI players, and when it is their turn,
it prompts the AI to play a move and moves on to the next player.

### Clicks on the cells in the hand

The clicks on the left and right of the panel will select the cell and highlight it on the view,
only if it is that person's turn.
The user can then click the same cell to deselect it.
If it is not the users turn, they are not allowed to select their cells.
This is also applicable at the end of the game.
After the cell is selected, clicking a yellow Wspace on board will play the cell.

### Clicks on the board

If a cell is selected by specified by the previous section
any click on the board will call the model to place the cell at the exact location of the click,
if the tile is empty.
Once a cell is played from the hand the turn moves on
and the next player can select and place a cell.
This continues until the game is determined to be over.

### Homework 7 Implementation Changes
The ThreeTrioGUIController has been changed to have no control over the player actions.
This is required as each player now receives their own view and a relevant controller for
them. This new PlayerController implementation now is in control of when and what the Player 
can play, in addition it controls the view given to the player. 

The overall controller, ThreeTrioGuiController, makes sure that the players play in turn. When it
is a player's turn, it notifies them, when a player plays a card, their controller calls the main
controller to change the turn to the next player. This goes until the game is over.


## Starting the GUI Implementation
### Homework 6
```java
public class Main{
  public static void main(String[] args) {
      ThreeTrioModel model = new ThreeTrioGameBuilder()
              .setGrid("chess.txt")
              .setCells("completeDeck.txt")
              .massSetPlayers(List.of(StrategyEnum.DEFENSIVE_STRAT,StrategyEnum.CAPTURE_STRAT), 26)
              .setupPlayers(ThreeTrioColor.BLUE, null).play(new ThreeTrioGameModel(), true);
      playGUIGame(model);
      
      TTGUIController controller = new ThreeTrioGUIController(model, true);
      TTGUIView view = new ThreeTrioGUIView(new ThreeTrioModelView(model), controller);
      controller.playGame(view);
    }
}
```

This is some example code to run the game with 26 players
on the board, chess.txt, and the deck, completeDeck.txt,in the GUI. This creates a game with
1 Human and 25 AI players. These AI will all have the same strategy of playing the most offensive of
the best defensive moves as discussed in the AI section above. Once this code runs it will start the
game in a new JFrame.


### Homework 7
The implemented main is fully equip with arguments.
These are what the arguments stand for:

1. path to grid config file
2. path to cell config file

Any other arguments sets the players. The format is: 
* "&lt;number of players&gt; &lt;strategies for AI seperated by commas or player&gt;"
* "&lt;ThreeTrioColor&gt; &lt;strategies for AI seperated by commas or player&gt;"

Be careful while using Color to set players as it may override the previously set players.

The possible strategies you can use:
- "player"
- "capture"
- "defensive"
- "corner"
- "random"

The "player" string can not be combined with any other strategies.
The main also assign a view to only human players, and does not assign a view to the AI players.

Example:
````
chess.txt completeDeck.txt 3 defensive,capture 2 player 40 random BLUE defensive
````
This would create a game on the board chess.txt using the deck completeDeck.txt with:

* 2 Pipe(defensive,capture) AIs (as one got overwritten by BLUE defensive)
* 2 human player
* 40 random AI
* 1 defensive BLUE AI

This game would also only pop up with 2 view as there are only 2 human players.

If no arguments are given then the game will be made with the chess.txt as the board and 
completeDeck.txt as the deck with 1 human player as BLUE with 51 AI players
running the defensive,capture,random strategy.

### Disclaimer For Future Project

We were unaware that others would receive our implementation when creating it and as such 
added the ability to use many colors for the sake of making large Pixel Art boards playable 
faster and with more people. In addition, the rotation feature was added to allow for 
this feature and is normally turned off when having less than 2 Human Players active as 
knowing AI hands is not technically necessary. It was turned on in the example to showcase that 
this feature was implemented but is not required. Should rotation be turned off with more than
2 human players, the third player will not have their cells show up and since neither of the panels
are the third player's colors, the game will become stuck and cannot progress.

#### Update Disclaimer as of Project 7

We have now implemented a separate view and controller for each player to utilize. With this
addition we have made it so that rotation is automatically applied on the right side and removed
the boolean for rotation that previously existed. Furthermore, more colors have been added to 
expedite the process of testing our larger boards.

# Patch Notes

## 1.1 (Homework 6)

### Everything new
* [AI](#overview-of-the-ai)
* [Player in the builder](#adding-players)
* [GUI(Controller + View)](#overview-of-gui-implementation)
* [Patch Notes](#patch-notes)

### Small Changes
* Coding:
  * Changed naming convention from "Card" to "Cell"

* Decks of cells:
  * RealBigDeck.txt changes
    * Added Juju cell
    * Buffed SelfEval cell to 4 4 4 4 (300% stat increase on every direction)
  * Added completeDeck.txt
    * Every iteration of a cell has been added to this deck

* Boards:
  * Added chess.txt board for higher level play
  * Added 1by1.txt board to view a tie game easily

* Colors
  * Added 24 colors:
    * APRICOT
    * CAESAR
    * DAWN
    * EEYORE
    * FAIRYLIGHT
    * GREEN
    * HELIOTROPE
    * ICE_BLUE
    * JADE
    * KETCHUP
    * LAPIS_LAZULI
    * MAGENTA
    * NEVA
    * OGRE
    * PURPLE
    * QUINACRIDONE_ROSE_VIOLET
    * SAFETY_ORANGE
    * TERRACOTTA
    * UBE
    * VAULT
    * WASABI
    * XYLOPHONE
    * YUCCA
    * ZUMTHOR
  * For large pixel art boards, we found that 2 colors was not enough, so we added more to match the alphabet

* Files:
  * Added strategy-transcript.txt
  * README:
    * Updated README to contain new information.

## 1.2 (Homework 7)
### Everything new
* [View](#homework-7-implementation-changes)
* [Controller](#homework-7-implementation-changes-1)
* [Main class](#homework-7)
### Small Changes

* Decks of cells:
  * RealBigDeck.txt changes
    * Added Jason's ping on Intellij cell
    * Added Jason's ping on Valorant cell
    * Added Yuval town building speed cell
    * Slight nerf on self-eval cell to 4 3 4 4 (25% damage reduction on the south value)

* Boards:
  * Added maxBoard.txt
  * Added hehehawhaw.txt

* Colors
  * Added 26 colors:
    * ATOMIC_TANGERINE
    * BAMBOO
    * CADILLAC
    * DARIO
    * EGGPLANT
    * FISH_N_CHIPS
    * GOSSAMER
    * HALF_SEA_FROG
    * IKO_IKO
    * JUPITER_PINK
    * KWILA
    * LAWN_GREEN
    * MEDIUM_SLATE_BLUE
    * NEON_CYAN
    * ONAHAU
    * PAPAYA
    * QUINACRIDONE_RED_VIOLET
    * ROSE_QUARTZ
    * SONIC_BOOM
    * TWITTER_BLUE
    * UNDERCURRENT 
    * VIRIDIAN
    * WATERLOO_AND_CITY_LINE
    * XANADU
    * YABBADABBADOO
    * ZODIAC
  * Replaced MAGENTA with MUSHY_PEAS
    * After adding FISH_N_CHIP, we decided we had to add MUSHY_PEAS for the full experience
  * Moved the char mapping of the colors to the text view, so we are not limited in the numbers of colors we can add to the GUI view.

* Files:
  * README:
    * Updated README to contain new information.

* Increased Performance:
  * Less lag when choosing a card from the hand, especially in the case of larger boards
    * Highly requested by player-base, Patrick and Jason