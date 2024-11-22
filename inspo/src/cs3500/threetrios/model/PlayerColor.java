package cs3500.threetrios.model;

/**
 * Represents the two players in a game of Three Trios: Player Red and Player Blue.
 */
public enum PlayerColor {
  RED("RED"), BLUE("BLUE");

  private final String stringRep;

  PlayerColor(String str) {
    stringRep = str;
  }

  /**
   * Returns the string representation of the player.
   *
   * @return the string representation of the player
   */
  public String toString() {
    return stringRep;
  }
}
