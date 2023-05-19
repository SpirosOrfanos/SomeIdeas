package com.etraveli.cardcost.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyBoAuth;
import com.etraveli.cardcost.domain.dto.CardCost;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ManagementQueryApi {

  @GetMapping
  @OnlyBoAuth
  public ResponseEntity<List<CardCost>> getCardCosts(
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {

    List<CardCost> costs = new ArrayList<>();
    costs.add(new CardCost("22", new BigDecimal("22")));
    costs.add(new CardCost("122", new BigDecimal("33")));
    return ResponseEntity.status(HttpStatus.OK).body(costs);
  }


}
