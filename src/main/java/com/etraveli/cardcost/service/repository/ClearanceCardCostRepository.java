package com.etraveli.cardcost.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;

public interface ClearanceCardCostRepository extends JpaRepository<ClearanceCardCost, String> {
  
}
