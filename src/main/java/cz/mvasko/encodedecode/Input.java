package cz.mvasko.encodedecode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Input {

  @NotBlank(message = "Input text is required")
  private String text;

  @NotBlank(message = "Codec is required")
  private String codec;

  @NotNull(message = "Direction is required")
  private Direction direction;

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public String getCodec() {
    return codec;
  }

  public void setCodec(final String codec) {
    this.codec = codec;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(final Direction direction) {
    this.direction = direction;
  }

}
