package com.etraveli.cardcost.api;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyUserAuth;
import com.etraveli.cardcost.domain.dto.CardBinCost;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.external.BinInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment-cards-cost")
@RequiredArgsConstructor
public class CardCostApi {
  
  @Autowired
  private BinInfoService binInfoService;

  @PostMapping
  @OnlyUserAuth
  public ResponseEntity<CardCost> getCardCost(@Valid @RequestBody CardBinCost cardBinCost) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CardCost.builder().cost(new BigDecimal("111")).country("ddd").build());
  }


}
