package com.alver.core.util;

import static org.immutables.value.Value.Parameter;

import java.util.Optional;

@Immutable
public interface Tuple<A, B> {
  @Parameter
  Optional<A> a();

  @Parameter
  Optional<B> b();
}
