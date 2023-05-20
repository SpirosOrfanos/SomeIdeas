package com.etraveli.cardcost.service.internal;

import org.springframework.stereotype.Service;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.CardInfoServiceDao;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class ManagementCommandService {

  private CardInfoServiceDao cardInfoServiceDao;

  public ManagementCommandService(
      @Qualifier("cardInfoServiceDao") CardInfoServiceDao cardInfoServiceDao) {
    this.cardInfoServiceDao = cardInfoServiceDao;
  }

  public void delete(String country) {
    cardInfoServiceDao.delete(country);
  }

  public void update(String country, CardCost cardCost) {
    cardInfoServiceDao.get(country).ifPresent(clearance -> {
      clearance.setAmount(cardCost.getCost());
      cardInfoServiceDao.update(clearance);
    });
  }

  public CardCost create(CardCost cardCost) {
    cardInfoServiceDao.save(
        ClearanceCardCost.builder().amount(cardCost.getCost()).id(cardCost.getCountry()).build());
    return cardCost;
  }

}
