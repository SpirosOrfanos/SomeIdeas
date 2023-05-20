package com.etraveli.cardcost.service.internal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.CardInfoServiceDao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagementCommandService {

  private CardInfoServiceDao cardInfoServiceDao;

  public ManagementCommandService(
      @Qualifier("cardInfoServiceDao") CardInfoServiceDao cardInfoServiceDao) {
    this.cardInfoServiceDao = cardInfoServiceDao;
  }

  public void delete(String country) {
    log.info("{}", country);
    cardInfoServiceDao.delete(country);
  }

  public void update(String country, CardCost cardCost) {
    log.info("{} {}", country, cardCost);
    cardInfoServiceDao.get(country).ifPresent(clearance -> {
      clearance.setAmount(cardCost.getCost());
      cardInfoServiceDao.update(clearance);
    });
  }

  public CardCost create(CardCost cardCost) {
    log.info("{}", cardCost);
    cardInfoServiceDao.save(
        ClearanceCardCost.builder().amount(cardCost.getCost()).id(cardCost.getCountry()).build());
    return cardCost;
  }

}
