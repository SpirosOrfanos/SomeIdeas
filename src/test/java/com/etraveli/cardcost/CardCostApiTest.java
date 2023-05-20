package com.etraveli.cardcost;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.etraveli.cardcost.domain.dto.CardBinCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.external.CardInfoService;
import com.etraveli.cardcost.utils.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CardCostApiTest {

  @Autowired
  protected MockMvc mockMvc;
  
  @MockBean
  private CardInfoService binInfoService;
  
  private CardBinCost card = CardBinCost.builder()
      .cardNumber("1234567891234567")
      .build();
  private CardBinCost badReqCard = CardBinCost.builder()
      .cardNumber("123456789123456")
      .build();
  
  @Test
  void getCardCost() throws Exception{
    when(binInfoService.retrieveBinInfo("123456")).thenReturn(CardCost.builder()
        .country("gr")
        .cost(BigDecimal.TEN).build());
    mockMvc.perform(post("/payment-cards-cost")
        .content(TestUtils.toJson(card))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.cost", Matchers.equalTo(10)))
    .andExpect(jsonPath("$.country", Matchers.equalTo("gr")));
  }
  
  @Test
  void getWronfInput() throws Exception{
  
    mockMvc.perform(post("/payment-cards-cost")
        .content(TestUtils.toJson(badReqCard))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest());
  }
  
  @Test
  void getCardCostNotFound() throws Exception{
    when(binInfoService.retrieveBinInfo("123456"))
    .thenThrow(new ActionNotSupportedException("") );
    mockMvc.perform(post("/payment-cards-cost")
        .content(TestUtils.toJson(card))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isPreconditionFailed());
  }
  
}
