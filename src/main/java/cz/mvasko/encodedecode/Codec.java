package cz.mvasko.encodedecode;

/**
 * Algorithms (codecs) should implement this interface.
 */
public interface Codec {

  /**
   * Encode input string to string output.
   * @param input Input string
   * @return Encoded output
   */
  String encode(String input);

  /**
   * Decodes input string to string output.
   * @param input Input string
   * @return Decoded output
   */
  String decode(String input);

  /**
   * Supported algorithm (codec name). It is displayed in the codec dropdown. Must be unique.
   * @return Supported algorithm (codec name)
   */
  String getSupportedAlgorithm();

}
