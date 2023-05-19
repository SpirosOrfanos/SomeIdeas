package com.etraveli.cardcost.service.external;

import java.math.BigDecimal;
import java.util.function.Function;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.etraveli.cardcost.bintable.ResponseItem;
import com.etraveli.cardcost.bintable.ResponseItemData;
import com.etraveli.cardcost.bintable.ResponseItemDataCountry;
import com.etraveli.cardcost.config.BinRestClientConfigProperties;
import com.etraveli.cardcost.config.JwtService;
import com.etraveli.cardcost.domain.dto.CardBinCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BinInfoService {
  
  private final BinRestClient binRestClient;
  private final BinRestClientConfigProperties binRestClientConfigProperties;
  private Mono<CardCost> fallbackMono = Mono.defer(() ->Mono.empty());
  
  public Mono<CardCost> retrieveBinInfo(final String bin) {
    return binRestClient.get(bin, binRestClientConfigProperties.getApiKey())
    //.onErrorComplete(e -> Mono.just(CardCost.builder().build())
    .map(ResponseItem::getData)
    .map(ResponseItemData::getCountry)
    .map(ResponseItemDataCountry::getCurrencyCode)
    .map(value -> CardCost.builder().country(value).cost(cost(value)).build())
    .switchIfEmpty(fallbackMono)
    .onErrorResume(e -> fallbackMono)
    ;
  }
  
  public BigDecimal cost(String country) {
    return new BigDecimal("100");
  }
  }

  
}
