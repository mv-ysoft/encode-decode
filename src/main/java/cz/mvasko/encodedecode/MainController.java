package cz.mvasko.encodedecode;

import cz.mvasko.encodedecode.codec.CodecManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private CodecManager codecManager;

  @GetMapping
  public String showMainForm(Input input) {
    return "main";
  }

  @PostMapping
  public String process(@Valid Input input, BindingResult bindingResult, Model model) {
    String output = null;
    switch (input.getDirection()) {
      case ENCODE:
        output = codecManager.encode(input.getText(), input.getCodec());
        break;
      case DECODE:
        output = codecManager.decode(input.getText(), input.getCodec());
        break;
    }
    model.addAttribute("output", output);
    model.addAttribute("input", input);
    return "main";
  }

  // Always add codecs to model.
  @ModelAttribute("codecs")
  public List<String> populateCodecs() {
    return codecManager.getCodecNamesList();
  }

}
