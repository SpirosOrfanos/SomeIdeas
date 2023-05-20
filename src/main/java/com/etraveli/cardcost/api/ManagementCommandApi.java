package com.etraveli.cardcost.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.etraveli.cardcost.annotations.OnlyBoAuth;
import com.etraveli.cardcost.domain.dto.CardCost;
import com.etraveli.cardcost.service.internal.ManagementCommandService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@RequestMapping("/api/v1/admin")
@Slf4j
public class ManagementCommandApi {

  @Autowired
  private ManagementCommandService managementCommandService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @OnlyBoAuth
  public ResponseEntity<CardCost> createCard(@Valid @RequestBody CardCost cardCost) {
    log.info("{}", cardCost);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(managementCommandService.create(cardCost));
  }

  @PatchMapping(value = {"/{country}"}, path = {"/{country}"},
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @OnlyBoAuth
  public void updateCard(
      @PathVariable(name = "country", value = "country", required = true) String country,
      @RequestBody CardCost cardCost) {
    log.info("{} {}", country, cardCost);
    managementCommandService.update(country, cardCost);
  }

  @DeleteMapping(value = {"/{country}"}, path = {"/{country}"})
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @OnlyBoAuth
  public void deleteCard(
      @PathVariable(name = "country", value = "country", required = true) String country) {
    log.info("{}", country);
    managementCommandService.delete(country);
  }

  /* In case we need batch */
  /*
  @DeleteMapping
  @OnlyBoAuth
  public ResponseEntity deleteBatch(@RequestParam(name = "countries", value = "countries",
      required = true) List<String> country) {
    return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
  }
  */



}
