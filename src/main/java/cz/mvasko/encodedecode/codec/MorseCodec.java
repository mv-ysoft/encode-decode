package cz.mvasko.encodedecode.codec;

import cz.mvasko.encodedecode.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.lang.Character.isLetter;
import static java.lang.Character.isWhitespace;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * <p>
 * Morse code codec. It supports English alphabet, digits and basic punctuation.
 * </p>
 * <p>
 * In encode direction it accepts words in English alphabet, separated by non-word characters. Successive whitespace
 * is collapsed to one. Input is trimmed.
 * It converts to dots and hyphens,
 * letters are separated by one slash, words (separated with whitespace) with two slashes.
 * </p>
 * <p>
 * In decode direction it accepts letters of dots and hyphens with letters separated by one slash and words with two
 * slashes and outputs space separated lowercase letters. Input whitespace is stripped out.
 * </p>
 * <p>
 * Unknown input is replaced by question marks.
 * </p>
 */
@Component
public class MorseCodec implements Codec {

  private static final String SUPPORTED_ALGORITHM = "morse";
  private static final Logger log = LoggerFactory.getLogger(MorseCodec.class);

  private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
  private static final Pattern DECODE_SPLIT_PATTERN = Pattern.compile("/");

  private static final Map<Integer, String> LETTERS_ENCODE_MAP = Map.ofEntries(
        entry((int) 'a', ".-"),
        entry((int) 'b', "-..."),
        entry((int) 'c', "-.-."),
        entry((int) 'd', "-.."),
        entry((int) 'e', "."),
        entry((int) 'f', "..-."),
        entry((int) 'g', "--."),
        entry((int) 'h', "...."),
        entry((int) 'i', ".."),
        entry((int) 'j', ".---"),
        entry((int) 'k', "-.-"),
        entry((int) 'l', ".-.."),
        entry((int) 'm', "--"),
        entry((int) 'n', "-."),
        entry((int) 'o', "---"),
        entry((int) 'p', ".--."),
        entry((int) 'q', "--.-"),
        entry((int) 'r', ".-."),
        entry((int) 's', "..."),
        entry((int) 't', "-"),
        entry((int) 'u', "..-"),
        entry((int) 'v', "...-"),
        entry((int) 'w', ".--"),
        entry((int) 'x', "-..-"),
        entry((int) 'y', "-.--"),
        entry((int) 'z', "--.."),
        entry((int) '0', "-----"),
        entry((int) '1', ".----"),
        entry((int) '2', "..---"),
        entry((int) '3', "...--"),
        entry((int) '4', "....-"),
        entry((int) '5', "....."),
        entry((int) '6', "-...."),
        entry((int) '7', "--..."),
        entry((int) '8', "---.."),
        entry((int) '9', "----."),
        entry((int) '.', ".-.-.-"),
        entry((int) ',', "--..--"),
        entry((int) '?', "..--.."),
        entry((int) '\'', ".----."),
        entry((int) '!', "-.-.--"),
        entry((int) '/', "-..-."),
        entry((int) '(', "-.--."),
        entry((int) ')', "-.--.-"),
        entry((int) '&', ".-..."),
        entry((int) ':', "---..."),
        entry((int) ';', "-.-.-."),
        entry((int) '=', "-...-"),
        entry((int) '+', ".-.-."),
        entry((int) '-', "-....-"),
        entry((int) '_', "..--.-"),
        entry((int) '"', ".-..-."),
        entry((int) '$', "...-..-"),
        entry((int) '@', ".--.-.")
  );

  // Make a reverse map
  private static final Map<String, String> LETTERS_DECODE_MAP = LETTERS_ENCODE_MAP.entrySet().stream()
        .collect(toUnmodifiableMap(Entry::getValue, entry -> new String(Character.toChars(entry.getKey()))));

  @Override
  public String getSupportedAlgorithm() {
    return SUPPORTED_ALGORITHM;
  }

  @Override
  public String encode(String input) {
    Objects.requireNonNull(input, "input is null");
    input = encodeCleanup(input);

    return input.codePoints()
          .mapToObj(cp -> {
      if (isLetter(cp)) {
        // Convert letters to lowercase first
        int lowercased = Character.toLowerCase(cp);
        return LETTERS_ENCODE_MAP.containsKey(lowercased)
          ? LETTERS_ENCODE_MAP.get(lowercased)
          : "?";
      } else if(LETTERS_ENCODE_MAP.containsKey(cp)) {
        return LETTERS_ENCODE_MAP.get(cp);
      } else if (isWhitespace(cp)) {
        return "";
      } else {
        return "?";
      }
    }).collect(joining("/"));
  }

  @Override
  public String decode(String input) {
    Objects.requireNonNull(input, "input is null");
    input = decodeCleanup(input);

    return DECODE_SPLIT_PATTERN.splitAsStream(input)
          .peek(token -> log.trace("Decoding token \"{}\"", token))
          .map(token -> {
            if (LETTERS_DECODE_MAP.containsKey(token)) {
              return LETTERS_DECODE_MAP.get(token);
            } else if ("".equals(token)) {
              return " ";
            } else {
              return "?";
            }
          }).collect(joining(""));
  }

  private String encodeCleanup(String input) {
    return WHITESPACE_PATTERN.matcher(input).replaceAll(" ").trim();
  }

  private String decodeCleanup(String input) {
    return WHITESPACE_PATTERN.matcher(input).replaceAll("");
  }
}
