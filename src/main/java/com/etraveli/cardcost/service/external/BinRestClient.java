package com.etraveli.cardcost.service.external;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import com.etraveli.cardcost.bintable.ResponseItem;
import reactor.core.publisher.Mono;

public interface BinRestClient {

  @GetExchange("/v1/{bin}")
  Mono<ResponseItem> get(@PathVariable String bin, @RequestParam(name = "api_key", value = "api_key") String api_key);
}
