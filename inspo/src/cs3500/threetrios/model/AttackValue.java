package cs3500.threetrios.model;

/**
 * Represents an attack value on a card in Three Trios. Attack values are integers from 1-9 and
 * the letter A, representing 10 from hexadecimal.
 */
public enum AttackValue {
  one(1), two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), A(10);

  private final int value;

  AttackValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    if (value == 10) {
      return "A";
    } else {
      return String.valueOf(value);
    }
  }

  /**
   * Returns the integer representation of the attack value.
   *
   * @return the integer representation of the attack value
   */
  public int getValue() {
    return value;
  }
}
