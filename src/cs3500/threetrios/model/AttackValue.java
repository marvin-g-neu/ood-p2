package cs3500.threetrios.model;

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
  
  /**
   * Constructs an AttackValue with the given strength.
   * 
   * @param strength the strength of the attack value
   */
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
}
