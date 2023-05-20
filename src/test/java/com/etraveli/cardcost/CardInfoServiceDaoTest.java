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
public class CardInfoServiceDaoTest {

  @Autowired
  private CardInfoServiceDao cardInfoServiceDao;
  
  @Test
  public void update() {
    ClearanceCardCost cost = ClearanceCardCost.builder().amount(BigDecimal.TEN)
    .id("gr")
    .build();
    ClearanceCardCost clearanceCardCost = cardInfoServiceDao
        .save(cost);
    assertEquals(clearanceCardCost.getAmount(), BigDecimal.TEN);
    assertThrows(ActionNotSupportedException.class, () -> cardInfoServiceDao.getAll());
    clearanceCardCost.setAmount(BigDecimal.ONE);
    cardInfoServiceDao.update(clearanceCardCost);
    assertEquals(cardInfoServiceDao.get("gr").get().getAmount(), new BigDecimal("1.00"));
    assertEquals(cardInfoServiceDao.getPaginated(PageRequest.of(0, 10)).size(), 1);
    cardInfoServiceDao.delete("gr");
    assertEquals(cardInfoServiceDao.getPaginated(PageRequest.of(0, 10)).size(), 0);
  }
}
