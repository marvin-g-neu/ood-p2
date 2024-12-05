package cs3500.threetrios.model.card;

/**
 * Represents the strength of an attack value ranging from
 * 1-9 with A from hexadecimal representing the value 10.
 */
public enum AttackValue {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  A(10);

  // the strength of the attack value
  private final int strength;

  AttackValue(int strength) {
    this.strength = strength;
  }

  /**
   * Gets the strength of the attack value.
   *
   * @return the strength of the attack value
   */
  public int getStrength() {
    return strength;
  }

  @Override
  public String toString() {
    if (strength == 10) {
      return "A";
    }
    return strength + "";
  }

  /**
   * Gets attack value based off a given int.
   *
   * @param strength the int to match an AttackValue
   * @return the AttackValue matching the int
   * @throws IllegalArgumentException if the int is not between 1 and 10
   */
  public static AttackValue getValue(int strength) {
    switch (strength) {
      case 1:
        return ONE;
      case 2:
        return TWO;
      case 3:
        return THREE;
      case 4:
        return FOUR;
      case 5:
        return FIVE;
      case 6:
        return SIX;
      case 7:
        return SEVEN;
      case 8:
        return EIGHT;
      case 9:
        return NINE;
      case 10:
        return A;
      default:
        throw new IllegalArgumentException("Invalid strength");
    }
  }
}
