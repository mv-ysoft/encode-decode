package cz.mvasko.encodedecode.codec;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MorseCodecTest {

  private MorseCodec codec;

  @Before
  public void setUp() throws Exception {
    this.codec = new MorseCodec();
  }

  @Test
  public void getSupportedAlgorithm() {
    assertThat(codec.getSupportedAlgorithm()).isEqualTo("morse");
  }

  @Test
  public void encodeSos() {
    assertThat(codec.encode("SOS")).isEqualTo(".../---/...");
  }

  @Test
  public void decodeSos() {
    assertThat(codec.decode(".../---/..."))
          .isEqualTo("sos");
  }
}