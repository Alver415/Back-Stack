package com.alver.app.service;

import com.alver.core.model.Entity;
import java.util.List;
import java.util.Optional;

public interface Service<T extends Entity> {

  List<T> getAll();

  Optional<T> getById(Long id);

  T create(T entity);

  T update(Long id, T entity);

  void delete(Long id);
}
