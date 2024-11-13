package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.GameState;
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
   * @throws IllegalArgumentException if model game state is not IN_PROGRESS
   */
  public GameRules(ThreeTriosModelInterface model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (model.getGameState() != GameState.IN_PROGRESS) {
      throw new IllegalArgumentException("Game must be in progress");
    }
    this.model = model;
  }

  @Override
  public boolean isLegalMove(Cell cell, CustomCard card) {
    if (cell == null || card == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    if (model.getGameState() != GameState.IN_PROGRESS) {
      throw new IllegalArgumentException("Game is not in progress");
    }
    return !cell.isHole() && cell.isEmpty();
  }

  @Override
  public Direction getOppositeDirection(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.WEST;
      case WEST:
        return Direction.EAST;
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
