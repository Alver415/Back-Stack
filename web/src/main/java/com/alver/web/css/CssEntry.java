package com.alver.web.css;

import com.alver.core.util.Immutable;

@Immutable
public interface CssEntry<T> {
  String name();

  T value();
}
