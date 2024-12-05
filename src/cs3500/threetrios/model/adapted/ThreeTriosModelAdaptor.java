package cs3500.threetrios.model.adapted;

import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.CellState;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.provider.model.ThreeTrioColor;
import cs3500.threetrios.provider.model.ThreeTrioModel;
import cs3500.threetrios.provider.model.cells.Cell;
import cs3500.threetrios.provider.model.cells.Directions;
import cs3500.threetrios.provider.model.players.ai.StrategyEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThreeTriosModelAdaptor implements ThreeTriosModelInterface {
  private class DeckCell implements Cell {
    private AttackValue north;
    private AttackValue south;
    private AttackValue east;
    private AttackValue west;
    private ThreeTrioColor cardColor;
    private boolean isHole;

    private DeckCell(boolean isHole) {
      this.north = null;
      this.south = null;
      this.east = null;
      this.west = null;
      this.cardColor = null;
      this.isHole = isHole;
    }

    private DeckCell(AttackValue north, AttackValue south, AttackValue east,
                     AttackValue west) {
      this.north = north;
      this.south = south;
      this.east = east;
      this.west = west;
      cardColor = null;
      isHole = false;
    }

    private DeckCell(AttackValue north, AttackValue south, AttackValue east, AttackValue west,
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
      return new DeckCell(north, south, east, west, cardColor);
    }

    @Override
    public boolean isaHole() {
      return isHole;
    }
  }

  private ThreeTrioModel providerModel;

  public ThreeTriosModelAdaptor(ThreeTrioModel providerModel) {
    this.providerModel = providerModel;
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck) {
    startGame(gameGrid, deck, true);
  }

  @Override
  public void startGame(Grid gameGrid, List<CustomCard> deck, boolean shuffle) {
    List<Cell> deckCells = new ArrayList<Cell>();
    for (CustomCard card : deck) {
      deckCells.add(new DeckCell(card.getAttackValue(Direction.NORTH),
          card.getAttackValue(Direction.SOUTH), card.getAttackValue(Direction.EAST),
          card.getAttackValue(Direction.WEST)));
    }

    Cell[][] board = new Cell[gameGrid.getRows()][gameGrid.getCols()];
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        cs3500.threetrios.model.cell.Cell cell = gameGrid.getCell(row, col);
        board[row][col] = new DeckCell(cell.isHole());
      }
    }

    Map<ThreeTrioColor, List<StrategyEnum>> map = new HashMap<ThreeTrioColor, List<StrategyEnum>>();
    map.put(ThreeTrioColor.RED, null);
    map.put(ThreeTrioColor.BLUE, null);


    providerModel.startGame(deckCells, board, map, shuffle);
  }

  @Override
  public void playTurn(int row, int col, int handIndex) {

  }

  @Override
  public Grid endGame() {
    return null;
  }

  @Override
  public ThreeTriosModelInterface copy() {
    return null;
  }

  @Override
  public CellState getCellStateAt(int row, int col) {
    return null;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return null;
  }

  @Override
  public List<CustomCard> getCurrentPlayerHand() {
    return List.of();
  }

  @Override
  public List<CustomCard> getPlayerHand(PlayerColor player) {
    return List.of();
  }

  @Override
  public Grid getGrid() {
    return null;
  }

  @Override
  public int getScore(PlayerColor player) {
    return 0;
  }

  @Override
  public GameState getGameState() {
    return null;
  }
}
