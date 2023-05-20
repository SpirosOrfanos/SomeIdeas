package com.etraveli.cardcost.domain.dbo;

import java.io.Serializable;
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
@Table(name = "bin_info")
public class BinInfo implements Serializable {
  @Id
  private String id;
  @Column(name = "COUNTRY")
  private String country;

}
