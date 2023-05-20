package com.etraveli.cardcost.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyBoAuth;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.internal.ManagementQueryService;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@RequestMapping("/api/v1/admin")
@Slf4j
public class ManagementQueryApi {

  @Autowired
  private ManagementQueryService managementQueryService;

  @GetMapping
  @OnlyBoAuth
  public ResponseEntity<List<CardCost>> getCardCosts(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "5") @Max(10) Integer size) {

    return ResponseEntity.status(HttpStatus.OK).body(managementQueryService.retrieve(page, size));
  }


}
