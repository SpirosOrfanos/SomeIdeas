package com.etraveli.cardcost.service;

import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.etraveli.cardcost.domain.dbo.ClearanceCardCost;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.repository.ClearanceCardCostRepository;

@Transactional(readOnly = true)
@Service("cardInfoServiceDao")
public class CardInfoServiceDao implements DaoService<ClearanceCardCost, String> {

  private ClearanceCardCostRepository clearanceCardCostRepository;

  public CardInfoServiceDao(ClearanceCardCostRepository clearanceCardCostRepository) {
    this.clearanceCardCostRepository = clearanceCardCostRepository;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  @CachePut(key = "#t.id", value = "countrycost")
  public void update(ClearanceCardCost t) {
    clearanceCardCostRepository.save(t);

  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  @CacheEvict(key = "#id", value = "countrycost")
  public void delete(String id) {
    clearanceCardCostRepository.deleteById(id);

  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  @CachePut(key = "#t.id", value = "countrycost")
  public ClearanceCardCost save(ClearanceCardCost t) {
    return clearanceCardCostRepository.save(t);
  }

  
  @Override
  @Cacheable(key = "#id", value = "countrycost")
  public Optional<ClearanceCardCost> get(String id) {
    return clearanceCardCostRepository.findById(id);
  }

  @Override
  public List<ClearanceCardCost> getAll() {
    throw new ActionNotSupportedException("Order update is not supported (yet)");
  }

  @Override
  public List<ClearanceCardCost> getPaginated(Pageable paging) {
    return clearanceCardCostRepository.findAll(paging).getContent();
  }



}
