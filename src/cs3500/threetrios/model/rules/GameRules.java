package cs3500.threetrios.model.rules;

import cs3500.threetrios.model.ThreeTriosModelInterface;
import cs3500.threetrios.model.PlayerColor;
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
  public String getOppositeDirection(String direction) {
    switch (direction) {
      case "NORTH": return "SOUTH";
      case "SOUTH": return "NORTH";
      case "EAST": return "WEST";
      case "WEST": return "EAST";
      default: return null; // should never happen
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
