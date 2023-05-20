package com.etraveli.cardcost.service.external;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
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
import reactor.core.publisher.Mono;

@Service
public class CardInfoService {

  private BinRestClient binRestClient;
  private BinRestClientConfigProperties binRestClientConfigProperties;
  @Qualifier("cardInfoServiceDao")
  private CardInfoServiceDao cardInfoServiceDao;
  @Qualifier("binInfoServiceDao")
  private BinInfoServiceDao binInfoServiceDao;

  private Mono<CardCost> fallbackMono = Mono.defer(Mono::empty);

  public CardInfoService(BinRestClient binRestClient,
      BinRestClientConfigProperties binRestClientConfigProperties,
      @Qualifier("cardInfoServiceDao") CardInfoServiceDao cardInfoServiceDao,
      @Qualifier("binInfoServiceDao") BinInfoServiceDao binInfoServiceDao) {
    this.binRestClient = binRestClient;
    this.binRestClientConfigProperties = binRestClientConfigProperties;
    this.cardInfoServiceDao = cardInfoServiceDao;
    this.binInfoServiceDao = binInfoServiceDao;

  }

  public CardCost retrieveBinInfo(String bin) {    
    Optional<BinInfo> binInfoOpt = binInfoServiceDao.get(bin);
    if (binInfoOpt.isPresent()) {
      String country = binInfoOpt.map(BinInfo::getCountry)
          .orElseThrow(() -> new ActionNotSupportedException(""));
      return cardInfoServiceDao.get(country).map(ClearanceCardCost::getAmount)
          .map(amount -> CardCost.builder().cost(amount).country(country).build())
          .orElse(CardCost.builder().cost(defaultAmount()).country(country).build());
    }
    return binRestClient.get(bin, binRestClientConfigProperties.getApiKey())
        .map(ResponseItem::getData).map(ResponseItemData::getCountry)
        .map(ResponseItemDataCountry::getCode)
        .doOnSuccess(code -> binInfoServiceDao
            .save(BinInfo.builder().country(code).id(bin).build()))
        .map(code -> cardCost.apply(code)).switchIfEmpty(fallbackMono)
        .onErrorResume(e -> fallbackMono).block();
  }


  private Function<String, CardCost> cardCost = country -> CardCost.builder().country(country)
      .cost(
          cardInfoServiceDao.get(country).map(ClearanceCardCost::getAmount).orElse(defaultAmount()))
      .build();

  private BigDecimal defaultAmount() {
    return cardInfoServiceDao.get("other").map(ClearanceCardCost::getAmount)
        .orElse(BigDecimal.ZERO);
  }


}
