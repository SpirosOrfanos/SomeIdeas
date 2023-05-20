package com.etraveli.cardcost.service;

import java.util.List;
import java.util.Optional;
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

  // Cache update
  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void update(ClearanceCardCost t) {
    clearanceCardCostRepository.save(t);

  }

  // Cache delete
  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void delete(String k) {
    clearanceCardCostRepository.deleteById(k);

  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public ClearanceCardCost save(ClearanceCardCost t) {
    return clearanceCardCostRepository.save(t);
  }

  // Cache
  @Override
  public Optional<ClearanceCardCost> get(String k) {
    System.out.println("cardInfoServiceDao > get "+ k);
    return clearanceCardCostRepository.findById(k);
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
