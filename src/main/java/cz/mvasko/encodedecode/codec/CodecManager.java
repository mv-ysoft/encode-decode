package cz.mvasko.encodedecode.codec;

import cz.mvasko.encodedecode.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Service
public class CodecManager {

  private static final Logger log = LoggerFactory.getLogger(CodecManager.class);

  private Map<String, Codec> codecs;

  @Autowired
  public CodecManager(List<Codec> codecs) {
    this.codecs = codecs.stream()
          .collect(toUnmodifiableMap(Codec::getSupportedAlgorithm, identity()));
  }

  public List<String> getCodecNamesList() {
    return codecs.keySet().stream()
          .sorted()
          .collect(toList());
  }

  public String encode(String input, String codec) {
    check(input, codec);
    String output = codecs.get(codec).encode(input);

    if (log.isInfoEnabled()) {
      log.info("Encoded \"{}\" to \"{}\" using codec \"{}\"", input, output, codec);
    }

    return output;
  }

  public String decode(String input, String codec) {
    check(input, codec);
    String output = codecs.get(codec).decode(input);

    if (log.isInfoEnabled()) {
      log.info("Decoded \"{}\" to \"{}\" using codec \"{}\"", input, output, codec);
    }

    return output;
  }

  private void check(String input, String codec) {
    Objects.requireNonNull(input, "input is required");
    Objects.requireNonNull(codec, "codec is required");
    if (!codecs.containsKey(codec)) {
      throw new IllegalArgumentException("Codec " + codec + " not found");
    }
  }

}
