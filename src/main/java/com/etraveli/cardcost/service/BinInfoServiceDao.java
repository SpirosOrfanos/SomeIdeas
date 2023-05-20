package com.etraveli.cardcost.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.etraveli.cardcost.domain.dbo.BinInfo;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.repository.BinInfoRepository;

@Transactional(readOnly = true)
@Service("binInfoServiceDao")
public class BinInfoServiceDao implements DaoService<BinInfo, String> {

  private BinInfoRepository binInfoRepository;

  public BinInfoServiceDao(BinInfoRepository binInfoRepository) {
    this.binInfoRepository = binInfoRepository;
  }

  @Override
  public void update(BinInfo t) {
    throw new ActionNotSupportedException("Order update is not supported (yet)");

  }

  @Override
  public void delete(String k) {
    throw new ActionNotSupportedException("Order update is not supported (yet)");

  }

  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  @Override
  public BinInfo save(BinInfo t) {
    return binInfoRepository.save(t);
  }

  // Cache
  @Override
  public Optional<BinInfo> get(String k) {
    return binInfoRepository.findById(k);
  }

  @Override
  public List<BinInfo> getAll() {
    throw new ActionNotSupportedException("Order update is not supported (yet)");
  }

  @Override
  public List<BinInfo> getPaginated(Pageable paging) {
    throw new ActionNotSupportedException("Order update is not supported (yet)");
  }



}
