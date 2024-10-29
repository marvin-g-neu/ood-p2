package cs3500.threetrios.controller.readers;

/**
 * An interface for classes which turns configuration files into data.
 *
 * @param <T> the type of data returned
 */
public interface ConfigurationFileReader<T> {
  /**
   * Reads data from a file and returns an Object which represents that data.
   *
   * @param filePath the path to the file being read
   * @return the Object representing the data
   * @throws IllegalArgumentException if the file is null
   * @throws IllegalArgumentException if the file is not formatted correctly
   * @throws IllegalArgumentException if the file cannot be read
   */
  T readFile(String filePath);
}
