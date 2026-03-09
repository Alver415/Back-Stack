package com.alver.web.model;

import static org.immutables.value.Value.*;

import com.alver.core.util.Immutable;
import java.util.List;

@Immutable
public interface ParentModel<T extends ParentModel<T>> extends Model<T> {

  @Default
  default List<Model<?>> children() {
    return List.of();
  }
}
