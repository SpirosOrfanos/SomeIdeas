package com.etraveli.cardcost;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.internal.ManagementCommandService;
import com.etraveli.cardcost.utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ManagementCommandApiTest {
  @Autowired
  protected MockMvc mockMvc;
  
  @MockBean
  private ManagementCommandService managementCommandService;

  private CardCost cardCost = CardCost.builder().cost(BigDecimal.TEN).country("gr").build();
  private CardCost badCard = CardCost.builder().country("gr").build();
  
  @Test
  void testCreate() throws Exception{
    when(managementCommandService.create(cardCost)).thenReturn(cardCost);
    mockMvc.perform(post("/api/v1/admin")
        .content(TestUtils.toJson(cardCost))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.cost", Matchers.equalTo(10)))
    .andExpect(jsonPath("$.country", Matchers.equalTo("gr")));
  }
  
  @Test
  void testCreateBWrongData() throws Exception{   
    mockMvc.perform(post("/api/v1/admin")
        .content(TestUtils.toJson(badCard))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  void testDelete() throws Exception{
   
    mockMvc.perform(delete("/api/v1/admin/{id}", "gr")
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNoContent());
  }
  
  @Test
  void testUpdate() throws Exception{
   
    mockMvc.perform(patch("/api/v1/admin/{id}", "gr")
        .content(TestUtils.toJson(cardCost))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isNoContent());
  }
  
  
}
