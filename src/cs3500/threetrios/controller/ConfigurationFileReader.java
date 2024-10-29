package cs3500.threetrios.controller;

import java.io.File;
import java.io.IOException;

/**
 * An interface for classes which take configuration files and turn them into data.
 *
 * @param <T> the type of data returned
 */
public interface ConfigurationFileReader<T> {
  /**
   * Reads data from a file and returns an Object which represents that data.
   *
   * @param file the file being read
   * @return the Object representing the data
   * @throws IllegalArgumentException if the file is null
   * @throws IllegalArgumentException if the file is not formatted correctly
   * @throws IOException              if the file cannot be read
   */
  T readFile(File file) throws IOException;
}
