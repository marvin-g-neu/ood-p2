package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.card.Direction;
import cs3500.threetrios.model.cell.Cell;
import cs3500.threetrios.model.card.CardColor;
import cs3500.threetrios.model.card.CustomCard;

/**
 * Abstract class containing common rule implementations for Three Trios.
 */
public abstract class GameRules implements RuleKeeper {
  protected final ThreeTriosModelInterface model;

  /**
   * Constructs a GameRules with the given model.
   *
   * @param model the game model
   * @throws IllegalArgumentException if model is null
   */
  public GameRules(ThreeTriosModelInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public boolean isLegalMove(Cell cell, CustomCard card) {
    return cell != null && !cell.isHole() && cell.isEmpty();
  }

  @Override
  public Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.EAST;
      case WEST:
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("Invalid direction"); // should never happen
    }
  }

  /**
   * Gets the CardColor for a player.
   *
   * @param player the player
   * @return the corresponding CardColor
   */
  protected CardColor getPlayerCardColor(PlayerColor player) {
    return player == PlayerColor.RED ? CardColor.RED : CardColor.BLUE;
  }
}
