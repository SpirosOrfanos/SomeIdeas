package com.etraveli.cardcost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.internal.ManagementCommandService;
import com.etraveli.cardcost.service.internal.ManagementQueryService;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ManagementCommandServiceTest {

  @Autowired
  private ManagementCommandService commandService;
  @Autowired
  private ManagementQueryService managementQueryService;

  @Test
  @Order(1)
  void test() {
    CardCost cardCost = CardCost.builder().cost(BigDecimal.TEN).country("gr").build();
    CardCost cardCost2 = CardCost.builder().cost(BigDecimal.ONE).country("gr").build();
    assertEquals("gr", commandService.create(cardCost).getCountry());
    List<CardCost> costs = managementQueryService.retrieve(0, 10);
    assertEquals(new BigDecimal("10.00"), costs.get(0).getCost());
    commandService.update("gr", cardCost2);

    costs = managementQueryService.retrieve(0, 10);
    assertEquals(1, costs.size());
    assertEquals("gr", costs.get(0).getCountry());
    assertEquals(new BigDecimal("1"), costs.get(0).getCost());

  }

  @Test
  @Order(2)
  void testDelete() {
    commandService.delete("gr");
    List<CardCost> costs = managementQueryService.retrieve(0, 10);
    assertEquals(0, costs.size());
  }
}
