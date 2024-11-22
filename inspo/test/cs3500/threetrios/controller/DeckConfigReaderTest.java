package cs3500.threetrios.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.controller.DeckConfigReader;

/**
 * Tests for DeckConfigReader.
 */
public class DeckConfigReaderTest {
  private DeckConfigReader configReader;

  @Before
  public void setup() {
    configReader = new DeckConfigReader();
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileBadPath() {
    configReader.readConfigFile("BADPATH.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNoLines() {
    configReader.readConfigFile("deckConfigFiles" + File.separator + "badConfigs" + File.separator
            + "noText.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNot5Strings() {
    configReader.readConfigFile("deckConfigFiles" + File.separator + "badConfigs" + File.separator
            + "not5Strings.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileAttackValueNotIntOrA() {
    configReader.readConfigFile("deckConfigFiles" + File.separator + "badConfigs" + File.separator
            + "attackValueNotIntOrA.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileAttackValueNotValidInt() {
    configReader.readConfigFile("deckConfigFiles" + File.separator + "badConfigs" + File.separator
            + "attackValueNotValidInt.txt");
  }

  @Test
  public void readConfigFileSuccessfully() {
    configReader.readConfigFile("deckConfigFiles" + File.separator + "smallDeck.txt");
    Assert.assertEquals("Correct card count", 6, configReader.getGameCards().size());
    Assert.assertEquals("Correct cards", "[CorruptKing 7 3 9 A, AngryDragon 2 8 9 9,"
                    + " WindBird 7 2 5 3, HeroKnight A 2 4 4, WorldDragon 8 3 5 7,"
                    + " SkyWhale 4 5 9 9]",
            configReader.getGameCards().toString());
  }
}
