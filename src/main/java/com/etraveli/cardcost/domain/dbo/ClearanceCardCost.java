package com.etraveli.cardcost.domain.dbo;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clearance")
public class ClearanceCardCost implements Serializable{
  
  @Id
  private String id;
  @Column(name = "AMOUNT")
  private BigDecimal amount;

}
