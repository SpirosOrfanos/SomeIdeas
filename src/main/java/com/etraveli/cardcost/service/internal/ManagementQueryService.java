package com.etraveli.cardcost.service.internal;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.CardInfoServiceDao;

@Service

public class ManagementQueryService {

  private CardInfoServiceDao cardInfoServiceDao;

  public ManagementQueryService(@Qualifier("cardInfoServiceDao")CardInfoServiceDao cardInfoServiceDao) {
    this.cardInfoServiceDao = cardInfoServiceDao;
  }
  
  public List<CardCost> retrieve(Integer page, Integer size) {
    return cardInfoServiceDao.getPaginated(PageRequest.of(page, size))
        .stream()
        .map(val -> CardCost.builder().cost(val.getAmount()).country(val.getId()).build())
        .toList();
  }
}
