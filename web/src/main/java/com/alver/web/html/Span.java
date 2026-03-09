package com.alver.web.html;

import static org.immutables.value.Value.Default;

import com.alver.core.util.Immutable;

@Immutable
public interface Span<T extends Span<T>> extends Element<T> {
  @Default
  default Tag tag() {
    return Tag.span;
  }
}
