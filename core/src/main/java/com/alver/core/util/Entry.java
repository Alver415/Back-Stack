package com.alver.core.util;

import org.immutables.value.Value.Parameter;

@Immutable
public interface Entry<K, V> {
  @Parameter
  K key();

  @Parameter
  V value();
}
