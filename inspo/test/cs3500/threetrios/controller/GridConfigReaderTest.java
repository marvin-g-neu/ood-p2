package cs3500.threetrios.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.controller.GridConfigReader;
import cs3500.threetrios.model.CellType;

/**
 * Tests for GridConfigReader.
 */
public class GridConfigReaderTest {
  private GridConfigReader configReader;

  @Before
  public void setup() {
    configReader = new GridConfigReader();
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileBadPath() {
    configReader.readConfigFile("BADPATH.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNoLines() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "noText.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileBadFirstLine() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "badFirstLine.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNonIntRowCol() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "nonIntRowCol.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNonPositiveRowCol() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "nonPositiveRowCol.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileNotEnoughRows() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "notEnoughRows.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileInvalidCellChar() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "invalidCellChar.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readConfigFileRowTooLong() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "rowsTooLong.txt");
  }

  @Test
  public void readConfigFileSuccessfully() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "5x3AllCardCells.txt");
    Assert.assertEquals("correct row count", 3, configReader.getRowCount());
    Assert.assertEquals("correct column count", 5, configReader.getColCount());
    Assert.assertEquals(List.of(CellType.CARDCELL, CellType.CARDCELL, CellType.CARDCELL,
            CellType.CARDCELL, CellType.CARDCELL), configReader.getCellTypes().get(0));
    Assert.assertEquals(List.of(CellType.CARDCELL, CellType.CARDCELL, CellType.CARDCELL,
            CellType.CARDCELL, CellType.CARDCELL), configReader.getCellTypes().get(1));
    Assert.assertEquals(List.of(CellType.CARDCELL, CellType.CARDCELL, CellType.CARDCELL,
            CellType.CARDCELL, CellType.CARDCELL), configReader.getCellTypes().get(2));
  }

  @Test
  public void readConfigFileExtraLines() {
    configReader.readConfigFile("gridConfigFiles" + File.separator + "badConfigs" + File.separator
            + "extraLines.txt");
    Assert.assertThrows("Extra lines ignored, only 3 rows", IndexOutOfBoundsException.class,
        () -> configReader.getCellTypes().get(3));
  }
}
