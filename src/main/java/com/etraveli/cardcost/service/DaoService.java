package com.etraveli.cardcost.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface DaoService<T, K> {

  public void update(T t);

  public void delete(K k);

  public T save(T t);

  public Optional<T> get(K k);

  public List<T> getAll();

  public List<T> getPaginated(Pageable paging);

}

