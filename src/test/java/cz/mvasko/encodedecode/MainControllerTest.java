package cz.mvasko.encodedecode;

import cz.mvasko.encodedecode.codec.CodecManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CodecManager codecManager;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void showMainForm() throws Exception {
    mockMvc.perform(
          get("/"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("Encode-Decode")));
  }

  @Test
  public void process() throws Exception {
    final String INPUT = "SOS";
    final String OUTPUT = ".../---/...";
    final String CODEC = "morse";

    when(codecManager.getCodecNamesList())
          .thenReturn(singletonList(CODEC));
    when(codecManager.encode(INPUT, CODEC))
          .thenReturn(".../---/...");

    this.mockMvc.perform(
          post("/")
                .param("text", INPUT)
                .param("codec", CODEC)
                .param("direction", "ENCODE"))
          .andExpect(content().string(containsString(OUTPUT)));
  }

}