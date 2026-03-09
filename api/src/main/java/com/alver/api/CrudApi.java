package com.alver.api;

import java.util.List;
import java.util.Optional;

public interface CrudApi<T> {

  T create(CreateRequest<T> createRequest);

  T update(Long id, UpdateRequest<T> updateRequest);

  void delete(Long id);

  List<T> getMany();

  Optional<T> getOne(Long id);
  
  Class<? extends CreateRequest<T>> createRequestType();
  
  Class<? extends UpdateRequest<T>> updateRequestType();
}
