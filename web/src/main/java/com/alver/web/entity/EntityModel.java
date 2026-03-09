package com.alver.web.entity;

import static org.immutables.value.Value.*;

import com.alver.core.model.Entity;
import com.alver.core.util.Immutable;
import com.alver.web.model.Model;

@Immutable
public interface EntityModel<T extends Entity> extends Model<EntityModel<T>> {

  @Parameter
  T entity();
}
