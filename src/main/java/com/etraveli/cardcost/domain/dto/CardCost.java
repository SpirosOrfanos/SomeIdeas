package com.etraveli.cardcost.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCost implements Serializable{
  @JsonProperty("country")
  private String country;
  @JsonProperty("cost")
  @NotBlank
  private BigDecimal cost;
}
