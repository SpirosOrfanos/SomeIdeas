package com.etraveli.cardcost.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyBoAuth;
import com.etraveli.cardcost.domain.dto.CardCost;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ManagementCommandApi {

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @OnlyBoAuth
  public ResponseEntity createCard(@RequestBody CardCost cardCost) {
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @OnlyBoAuth
  public ResponseEntity updateCard(@RequestBody List<CardCost> cardCost) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping
  @OnlyBoAuth
  public ResponseEntity deleteCard(@RequestParam(name = "countries", value = "countries",
      required = true) List<String> country) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


}
