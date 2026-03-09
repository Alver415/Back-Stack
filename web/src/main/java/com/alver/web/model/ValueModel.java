package com.alver.web.model;

import static org.immutables.value.Value.Parameter;

import com.alver.core.util.Immutable;

@Immutable
public interface ValueModel<T extends ValueModel<T, V>, V> extends Model<T> {

  @Parameter
  V value();
}
