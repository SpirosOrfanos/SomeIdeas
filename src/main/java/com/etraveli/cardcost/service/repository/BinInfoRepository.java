package com.etraveli.cardcost.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etraveli.cardcost.domain.dbo.BinInfo;

public interface BinInfoRepository extends JpaRepository<BinInfo, String> {
  
}
