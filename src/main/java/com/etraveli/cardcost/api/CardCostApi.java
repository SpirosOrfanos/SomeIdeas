package com.etraveli.cardcost.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyUserAuth;
import com.etraveli.cardcost.domain.dto.CardBinCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.external.CardInfoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment-cards-cost")
@Validated
@Slf4j
public class CardCostApi {

  @Autowired
  private CardInfoService binInfoService;

  @PostMapping
  @OnlyUserAuth
  public ResponseEntity<CardCost> getCardCost(@Valid @RequestBody CardBinCost cardBinCost) {
    log.info("{}", cardBinCost);
    return ResponseEntity.status(HttpStatus.OK)
    .contentType(MediaType.APPLICATION_JSON)
    .body(binInfoService.retrieveBinInfo(cardBinCost.getCardNumber().substring(0, 6)));
  }


}
