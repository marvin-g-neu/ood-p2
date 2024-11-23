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
  private Grid grid;

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
    executeBattlePhase(row, col, currentPlayer, grid);
  }

  @Override
  public void executeBattlePhase(int row, int col, PlayerColor currentPlayer, Grid givenGrid) {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Current player cannot be null");
    }
    if (row < 0 || row >= givenGrid.getRows() || col < 0 || col >= givenGrid.getCols()) {
      System.out.print(row + " " + col);
      throw new IllegalArgumentException("Coordinates must be in range");
    }
    if (model.getGameState() != GameState.IN_PROGRESS) {
      throw new IllegalStateException("Game state is not in progress");
    }

    Cell cell = givenGrid.getCell(row, col);
    if (cell.isHole() || cell.isEmpty()) {
      throw new IllegalArgumentException("Cell does not have a card");
    }
    Cell[] adjacentCells = givenGrid.getAdjacentCells(row, col);

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
    executeBattlePhase(newRow, newCol, currentPlayer, grid);
  }

  @Override
  public boolean isGameCompleted() {
    if (model.getGameState() == GameState.NOT_STARTED) {
      throw new IllegalStateException("Game is not started");
    }
    grid = model.getGrid();
    return grid.getEmptyCellCount() == 0;
  }

  @Override
  public int getPotentialFlips(int row, int col, int handIndex, PlayerColor currentPlayer) {
    if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getCols()) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    // create model
    ThreeTriosModelInterface modelCopy = model.copy();

    if (handIndex < 0 || handIndex >= modelCopy.getCurrentPlayerHand().size()) {
      throw new IllegalArgumentException("Invalid hand index");
    }

    // play card
    modelCopy.playTurn(row, col, handIndex);

    // Count opponent's cards before simulation
    int originalOpponentCards = 0;
    grid = model.getGrid();
    for (Cell cell : grid.getCardCells()) {
      if (!cell.isEmpty() && cell.getCellColor() != currentPlayer) {
        originalOpponentCards++;
      }
    }

    // Count opponent's cards after simulation
    int finalOpponentCards = 0;
    for (Cell cell : modelCopy.getGrid().getCardCells()) {
      if (!cell.isEmpty() && cell.getCellColor() != currentPlayer) {
        finalOpponentCards++;
      }
    }

    // The difference represents how many cards were flipped
    return originalOpponentCards - finalOpponentCards;
  }
}