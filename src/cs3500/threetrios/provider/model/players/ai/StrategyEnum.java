package cs3500.threetrios.provider.model.players.ai;

import java.util.Map;

/**
 * The types of strategy there are,
 * Useful for the AIFactory.
 */
public enum StrategyEnum {
  CAPTURE_STRAT,
  CORNER_STRAT,
  DEFENSIVE_STRAT,
  RANDOM_STRAT;

  //a string to StrategyEnum mapping
  private static Map<String, StrategyEnum> stringStrategyEnumMap = Map.ofEntries(
          Map.entry("capture", CAPTURE_STRAT),
          Map.entry("corner", CORNER_STRAT),
          Map.entry("defensive", DEFENSIVE_STRAT),
          Map.entry("random", RANDOM_STRAT)
  );

  /**
   * Given a string it returns a StrategyEnum.
   *
   * @param str the given string.
   * @return the corresponding StrategyEnum.
   * @throws IllegalArgumentException if the given String is not found to equate to a strategy.
   */
  public static StrategyEnum stringToStrategyEnum(String str) {
    if (!stringStrategyEnumMap.containsKey(str)) {
      throw new IllegalArgumentException("The given String is not an available strategy!");
    }
    return stringStrategyEnumMap.get(str.toLowerCase());
  }
}