package cs3500.threetrios.model.adapted;

import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.*;
import cs3500.threetrios.model.cell.ThreeTriosCell;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.ThreeTriosBoard;
import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.model.ThreeTrioModel;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.cells.Directions;
import cs3500.threetrios.provider.model.players.Player;
import cs3500.threetrios.provider.model.players.ai.StrategyEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ThreeTriosModelAdaptor implements ThreeTrioModel {
  ThreeTriosModelInterface origModel;

  private class CardCell implements Cell {
    private AttackValue north;
    private AttackValue south;
    private AttackValue east;
    private AttackValue west;
    private ThreeTrioColor cardColor;
    private boolean isHole;

    private CardCell(boolean isHole) {
      this.north = null;
      this.south = null;
      this.east = null;
      this.west = null;
      this.cardColor = null;
      this.isHole = isHole;
    }

    private CardCell(AttackValue north, AttackValue south, AttackValue east,
                     AttackValue west) {
      this.north = north;
      this.south = south;
      this.east = east;
      this.west = west;
      cardColor = null;
      isHole = false;
    }

    private CardCell(AttackValue north, AttackValue south, AttackValue east, AttackValue west,
                     ThreeTrioColor cardColor) {
      this(north, south, east, west);
      this.cardColor = cardColor;
    }

    @Override
    public int getValue(Directions dir) {
      switch (dir) {
        case NORTH:
          return north.getStrength();
        case SOUTH:
          return south.getStrength();
        case EAST:
          return east.getStrength();
        case WEST:
          return west.getStrength();
        default:
          throw new IllegalArgumentException("Unknown direction: " + dir);
      }
    }

    @Override
    public String toDisplayName(Directions dir) {
      switch (dir) {
        case NORTH:
          return north.toString();
        case SOUTH:
          return south.toString();
        case EAST:
          return east.toString();
        case WEST:
          return west.toString();
        default:
          throw new IllegalArgumentException("Unknown direction: " + dir);
      }
    }

    @Override
    public void changeColor(ThreeTrioColor color) {
      cardColor = color;
    }

    @Override
    public ThreeTrioColor getColor() {
      return cardColor;
    }

    @Override
    public Cell copy() {
      return new CardCell(north, south, east, west, cardColor);
    }

    @Override
    public boolean isaHole() {
      return isHole;
    }
  }

  public ThreeTriosModelAdaptor(ThreeTriosModelInterface model) {
    origModel = model;
  }

  @Override
  public void startGame(List<Cell> deck, Cell[][] board, Map<ThreeTrioColor,
      List<StrategyEnum>> strategyMap, boolean shuffle) {
    cs3500.threetrios.model.cell.Cell[][] cellGrid =
        new ThreeTriosCell[board.length][board[0].length];
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        cellGrid[row][col] = new ThreeTriosCell(board[row][col].isaHole());
      }
    }

    Grid grid = new ThreeTriosBoard(cellGrid);
    List<CustomCard> origDeck = new ArrayList<>();
    for (Cell cell : deck) {
      origDeck.add(new ThreeTriosCard(cell.toString(),
          AttackValue.getValue(cell.getValue(Directions.NORTH)),
          AttackValue.getValue(cell.getValue(Directions.SOUTH)),
          AttackValue.getValue(cell.getValue(Directions.EAST)),
          AttackValue.getValue(cell.getValue(Directions.WEST))));
    }

    origModel.startGame(grid, origDeck);
  }

  @Override
  public void playCell(Cell cell, int x, int y) {
  }

  @Override
  public Player getPlayer(ThreeTrioColor color) {
    return null;
  }

  @Override
  public List<Cell> getHand(ThreeTrioColor color) {
    PlayerColor origColor;
    if (color == ThreeTrioColor.RED) {
      origColor = PlayerColor.RED;
    } else if (color == ThreeTrioColor.BLUE) {
      origColor = PlayerColor.BLUE;
    } else {
      throw new IllegalArgumentException("Unknown color: " + color);
    }

    List<CustomCard> origHand = origModel.getPlayerHand(origColor);

    List<Cell> hand = new ArrayList<>();
    for (CustomCard card : origHand) {
      hand.add(new CardCell(card.getAttackValue(Direction.NORTH),
          card.getAttackValue(Direction.SOUTH), card.getAttackValue(Direction.EAST),
          card.getAttackValue(Direction.WEST), color));
    }

    return hand;
  }

  @Override
  public Cell[][] getBoard() {
    Grid grid = origModel.getGrid();
    Cell[][] board = new Cell[grid.getRows()][grid.getCols()];
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        cs3500.threetrios.model.cell.Cell cell = grid.getCell(row, col);
        if (cell.isHole()) {
          board[row][col] = new CardCell(true);
        } else if (cell.isEmpty()) {
          board[row][col] = new CardCell(false);
        } else {
          CustomCard card = cell.getCard();
          ThreeTrioColor color;
          if (card.getCurrentColor() == CardColor.RED) {
            color = ThreeTrioColor.RED;
          } else if (card.getCurrentColor() == CardColor.BLUE) {
            color = ThreeTrioColor.BLUE;
          } else {
            throw new IllegalStateException("Unexpected color: " + card.getCurrentColor());
          }

          board[row][col] = new CardCell(card.getAttackValue(Direction.NORTH),
              card.getAttackValue(Direction.SOUTH), card.getAttackValue(Direction.EAST),
              card.getAttackValue(Direction.NORTH), color);
        }
      }
    }

    return board;
  }

  @Override
  public List<ThreeTrioColor> getAllPlayers() {
    return List.of(ThreeTrioColor.RED, ThreeTrioColor.BLUE);
  }

  @Override
  public boolean isGameOver() {
    return origModel.getGameState() != GameState.IN_PROGRESS
        && origModel.getGameState() != GameState.NOT_STARTED;
  }

  @Override
  public boolean hasGameStarted() {
    return origModel.getGameState() != GameState.NOT_STARTED;
  }

  @Override
  public List<ThreeTrioColor> getWinner() {
    switch (origModel.getGameState()) {
      case RED_WIN:
        return List.of(ThreeTrioColor.RED);
      case BLUE_WIN:
        return List.of(ThreeTrioColor.BLUE);
      case DRAW:
        return null;
      default:
        throw new IllegalStateException("Game not completed");
    }
  }

  @Override
  public Map<ThreeTrioColor, Integer> getScores() {
    return Map.of(ThreeTrioColor.RED, origModel.getScore(PlayerColor.RED),
        ThreeTrioColor.BLUE, origModel.getScore(PlayerColor.BLUE));
  }
}
