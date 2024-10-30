package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.grid.Grid;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the basic Three Trios game rules.
 */
public class BasicThreeTriosGame extends GameRules {
    
  /**
   * Constructs a BasicThreeTriosGame with the given model.
   *
   * @param model the game model
   */
  public BasicThreeTriosGame(ThreeTriosModelInterface model) {
    super(model);
  }

  @Override
  public void executeBattlePhase(CustomCard placedCard, int row, int col, PlayerColor currentPlayer) {
    Queue<Coordinates> battleQueue = new LinkedList<>();
    Grid grid = model.getGrid();
    battleQueue.add(new Coordinates(row, col));

    while (!battleQueue.isEmpty()) {
      Coordinates coor = battleQueue.remove();
      CustomCard[] adjacentCards = grid.getAdjacentCards(coor.getRow(), coor.getColumn());
            
      // Check battles in all directions [NORTH, SOUTH, EAST, WEST]
      Direction[] directions = Direction.values();
      for (int i = 0; i < adjacentCards.length; i++) {
        CustomCard adjacentCard = adjacentCards[i];
        if (adjacentCard == null || 
            adjacentCard.getCurrentColor() == getPlayerCardColor(currentPlayer)) {
          continue;
        }

        CustomCard attackingCard = grid.getCell(coor.getRow(), coor.getColumn()).getCard();

        if (model.attackerWinsBattle(attackingCard, adjacentCard, directions[i])) {
          // Flip card and add to combo queue
          Coordinates adjPos = getAdjacentPosition(coor.getRow(), coor.getColumn(), directions[i]);
          grid.getCell(adjPos.getRow(), adjPos.getColumn())
              .flipCard(getPlayerCardColor(currentPlayer));
          battleQueue.add(adjPos);
        }
      }
    }
  }

  @Override
  public boolean isGameOver() {
    Grid grid = model.getGrid();
    return grid.getEmptyCellCount() == 0 || 
           model.getCurrentPlayerHand().isEmpty();
  }

  // Gets the adjacent position based on the direction.
  private Coordinates getAdjacentPosition(int row, int col, Direction direction) {
    switch (direction) {
      case NORTH: return new Coordinates(row - 1, col);
      case SOUTH: return new Coordinates(row + 1, col);
      case EAST: return new Coordinates(row, col + 1);
      case WEST: return new Coordinates(row, col - 1);
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }
}
