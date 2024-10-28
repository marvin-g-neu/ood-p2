package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.CustomCard;
import cs3500.threetrios.model.card.AttackValue;
import cs3500.threetrios.model.PlayerName;
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
  public void executeBattlePhase(CustomCard placedCard, int row, int col, PlayerName currentPlayer) {
    Queue<Coordinates> battleQueue = new LinkedList<>();
    Grid grid = model.getGrid();
    battleQueue.add(new Coordinates(row, col));

    while (!battleQueue.isEmpty()) {
      Coordinates coor = battleQueue.remove();
      CustomCard[] adjacentCards = grid.getAdjacentCards(coor.row, coor.col);
            
      // Check battles in all directions [north, south, east, west]
      String[] directions = {"NORTH", "SOUTH", "EAST", "WEST"};
      for (int i = 0; i < adjacentCards.length; i++) {
        CustomCard adjacentCard = adjacentCards[i];
        if (adjacentCard == null || 
            adjacentCard.getCurrentColor() == getPlayerCardColor(currentPlayer)) {
          continue;
        }

        CustomCard attackingCard = grid.getCell(coor.row, coor.col).getCard();
        AttackValue attackValue = getAttackValue(attackingCard, directions[i]);
        AttackValue defendValue = getAttackValue(adjacentCard, getOppositeDirection(directions[i]));

        if (model.attackerWinsBattle(attackValue, defendValue)) {
          // Flip card and add to combo queue
          Coordinates adjPos = getAdjacentPosition(coor.row, coor.col, directions[i]);
          grid.getCell(adjPos.row, adjPos.col)
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

  // Gets the attack value of a card based on the direction.
  private AttackValue getAttackValue(CustomCard card, String direction) {
    switch (direction) {
      case "NORTH": return card.getNorthStrength();
      case "SOUTH": return card.getSouthStrength();
      case "EAST": return card.getEastStrength();
      case "WEST": return card.getWestStrength();
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }

  // Gets the adjacent position based on the direction.
  private Coordinates getAdjacentPosition(int row, int col, String direction) {
    switch (direction) {
      case "NORTH": return new Coordinates(row - 1, col);
      case "SOUTH": return new Coordinates(row + 1, col);
      case "EAST": return new Coordinates(row, col + 1);
      case "WEST": return new Coordinates(row, col - 1);
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }
}
