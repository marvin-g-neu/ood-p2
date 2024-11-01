package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.GameState;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.grid.Grid;

/**
 * Implementation of the basic Three Trios game rules.
 */
public class BasicThreeTriosGame extends GameRules {
  private final Grid grid;

  /**
   * Constructs a BasicThreeTriosGame with the given model.
   *
   * @param model the game model
   * @throws IllegalArgumentException if model is null
   * @throws IllegalArgumentException if model game state is not IN_PROGRESS
   */
  public BasicThreeTriosGame(ThreeTriosModelInterface model) {
    super(model);
    grid = model.getGrid();
  }

  @Override
  public boolean attackerWinsBattle(CustomCard attacker,
                                    CustomCard defender, Direction attackDirection) {
    if (attacker == null || defender == null || attackDirection == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    int attackerStrength = attacker.getAttackValue(attackDirection).getStrength();
    return attackerStrength > defender.getAttackValue(
        getOppositeDirection(attackDirection)).getStrength();
  }

  @Override
  public void executeBattlePhase(int row, int col, PlayerColor currentPlayer) {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Current player cannot be null");
    }
    if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getCols()) {
      System.out.print(row + " " + col);
      throw new IllegalArgumentException("Coordinates must be in range");
    }
    if (model.getGameState() != GameState.IN_PROGRESS) {
      throw new IllegalStateException("Game state is not in progress");
    }

    Cell cell = grid.getCell(row, col);
    if (cell.isHole() || cell.isEmpty()) {
      throw new IllegalArgumentException("Cell does not have a card");
    }
    Cell[] adjacentCells = grid.getAdjacentCells(row, col);

    for (int d = 0; d < Direction.values().length; d++) {
      Cell adjacentCell = adjacentCells[d];
      if (adjacentCell == null || adjacentCell.isHole() || adjacentCell.isEmpty()
          || cell.getCellColor() == adjacentCell.getCellColor()) {
        continue;
      }
      Direction attackDirection = Direction.values()[d];
      if (attackerWinsBattle(cell.getCard(), adjacentCell.getCard(), attackDirection)) {
        // Recursively completes future battles
        adjacentCell.flipCard(getPlayerCardColor(cell.getCellColor()));
        battle(row, col, attackDirection, currentPlayer);
      }
    }
  }

  /**
   * Executes a battle at the given coordinates with the given direction.
   *
   * @param row the row of the cell
   * @param col the column of the cell
   * @param attackDirection the direction of the attack
   * @param currentPlayer the current player
   */
  private void battle(int row, int col, Direction attackDirection, PlayerColor currentPlayer) {
    int newRow = row;
    int newCol = col;
    switch (attackDirection) {
      case NORTH:
        newRow -= 1;
        break;
      case SOUTH:
        newRow += 1;
        break;
      case EAST:
        newCol += 1;
        break;
      case WEST:
        newCol -= 1;
        break;
      default: // should never happen
        throw new IllegalArgumentException("Unknown Direction");
    }
    executeBattlePhase(newRow, newCol, currentPlayer);
  }

  @Override
  public boolean isGameCompleted() {
    if (model.getGameState() == GameState.NOT_STARTED) {
      throw new IllegalStateException("Game is not started");
    }
    return grid.getEmptyCellCount() == 0;
  }
}