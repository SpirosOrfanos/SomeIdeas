package com.etraveli.cardcost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.CardInfoServiceDao;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class CardInfoServiceDaoTest {

  @Autowired
  private CardInfoServiceDao cardInfoServiceDao;
  
  @Test
  void update() {
    ClearanceCardCost cost = ClearanceCardCost.builder().amount(BigDecimal.TEN)
    .id("gr")
    .build();
    ClearanceCardCost clearanceCardCost = cardInfoServiceDao
        .save(cost);
    assertEquals(BigDecimal.TEN, clearanceCardCost.getAmount());
    assertThrows(ActionNotSupportedException.class, () -> cardInfoServiceDao.getAll());
    clearanceCardCost.setAmount(BigDecimal.ONE);
    cardInfoServiceDao.update(clearanceCardCost);
    assertEquals(new BigDecimal("1.00"), cardInfoServiceDao.get("gr").get().getAmount());
    assertEquals(1, cardInfoServiceDao.getPaginated(PageRequest.of(0, 10)).size());
    cardInfoServiceDao.delete("gr");
    assertEquals(cardInfoServiceDao.getPaginated(PageRequest.of(0, 10)).size(), 0);
  }
}
