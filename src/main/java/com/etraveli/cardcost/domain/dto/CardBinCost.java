package com.etraveli.cardcost.domain.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardBinCost implements Serializable {
  @JsonProperty("card_number")
  @Size(min = 16, message = "Minimum of 16 digits required")
  private String cardNumber;

}
