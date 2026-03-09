package com.alver.data.reader;

import com.alver.core.model.Entity;
import java.util.List;
import java.util.Optional;

public interface Reader<T extends Entity> {

  List<T> findAll();

  Optional<T> findById(Long id);
}
