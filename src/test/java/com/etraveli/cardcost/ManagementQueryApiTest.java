package com.etraveli.cardcost;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.internal.ManagementQueryService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ManagementQueryApiTest {

  @Autowired
  protected MockMvc mockMvc;
  @MockBean
  private ManagementQueryService managementQueryService;
  private List<CardCost> costs = List.of(CardCost.builder().country("gr").build(), 
      CardCost.builder().country("us").build());
  @Test
  void testRetrieve() throws Exception {
    when(managementQueryService.retrieve(1, 1))
    .thenReturn(costs);
    mockMvc.perform(get("/api/v1/admin")
        .queryParam("page", "1")
        .queryParam("size", "1")
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(jsonPath("$", Matchers.hasSize(2)));
    
  }
  
}