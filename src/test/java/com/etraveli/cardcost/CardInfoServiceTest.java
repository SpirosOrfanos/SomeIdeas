package com.etraveli.cardcost;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.etraveli.cardcost.bintable.ResponseItem;
import com.etraveli.cardcost.bintable.ResponseItemData;
import com.etraveli.cardcost.bintable.ResponseItemDataCountry;
import com.etraveli.cardcost.config.BinRestClientConfigProperties;
import com.etraveli.cardcost.domain.dbo.BinInfo;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.BinInfoServiceDao;
import com.etraveli.cardcost.service.CardInfoServiceDao;
import com.etraveli.cardcost.service.external.BinRestClient;
import com.etraveli.cardcost.service.external.CardInfoService;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureMockMvc
class CardInfoServiceTest {
  @MockBean
  private BinRestClient binRestClient;
  @MockBean
  private BinRestClientConfigProperties binRestClientConfigProperties;
  @MockBean
  private CardInfoServiceDao cardInfoServiceDao;
  @MockBean
  private BinInfoServiceDao binInfoServiceDao;
  @Autowired
  private CardInfoService cardInfoService;
  
  private String correctBin = "123456";
  private String problematicBin = "000000";
  private String binForDefaultCost = "111111";
  private String newBin   = "012345";
  private String apiKey     = "885544774477";
  private ResponseItem responseItem;
  private ClearanceCardCost clearanceCardCost;
  private BinInfo binInfo;
  private BinInfo problematicBinInfo;
  private BinInfo defaultCostBinInfo;
  private BinInfo newBinInfo;
  @BeforeEach
  public void before() {
    responseItem = new ResponseItem();
    ResponseItemData responseItemData = new ResponseItemData();
    ResponseItemDataCountry country = new ResponseItemDataCountry();
    country.setCode("eg");
    responseItemData.setCountry(country);
    responseItem.setData(responseItemData);
    binInfo = BinInfo.builder().country("gr").id(correctBin).build();
    problematicBinInfo = BinInfo.builder().country(null).id(correctBin).build();
    defaultCostBinInfo = BinInfo.builder().country("us").id(binForDefaultCost).build();
    newBinInfo =BinInfo.builder().country("eg").id(newBin).build();
    clearanceCardCost =  ClearanceCardCost.builder()
        .amount(BigDecimal.TEN).id("gr").build();
    when(binRestClientConfigProperties.getApiKey()).thenReturn(apiKey);
  }
  @Test
  void test() {
    when(binRestClient.get(newBin, apiKey)).thenReturn(Mono.just(responseItem));
    when(cardInfoServiceDao.get("gr")).thenReturn(Optional.of(clearanceCardCost));
    when(cardInfoServiceDao.get("us")).thenReturn(Optional.empty());
    when(cardInfoServiceDao.get("other")).thenReturn(Optional.of(ClearanceCardCost.builder().id("other").amount(BigDecimal.ONE).build()));
    when(cardInfoServiceDao.get("eg")).thenReturn(Optional.of(ClearanceCardCost.builder().id("eg").amount(BigDecimal.valueOf(11)).build()));
    when(binInfoServiceDao.get(correctBin)).thenReturn(Optional.of(binInfo));
    when(binInfoServiceDao.get(problematicBin)).thenReturn(Optional.of(problematicBinInfo));
    when(binInfoServiceDao.get(binForDefaultCost)).thenReturn(Optional.of(defaultCostBinInfo));
    when(binInfoServiceDao.save(newBinInfo)).thenReturn(newBinInfo);
    CardCost cardCost = cardInfoService.retrieveBinInfo(correctBin);
    assertEquals("gr", cardCost.getCountry());
    assertEquals(new BigDecimal("10"), cardCost.getCost());
    assertThrows(ActionNotSupportedException.class, () -> 
      cardInfoService.retrieveBinInfo(problematicBin));
    cardCost = cardInfoService.retrieveBinInfo(binForDefaultCost);
    assertEquals(new BigDecimal("1"), cardCost.getCost());
    cardCost = cardInfoService.retrieveBinInfo(newBin);
    assertEquals(new BigDecimal("11"), cardCost.getCost());
    assertEquals("eg", cardCost.getCountry());
  }
  
}
