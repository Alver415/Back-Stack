package com.alver.web.html;

import com.alver.core.util.Immutable;

import static org.immutables.value.Value.Default;

@Immutable
public interface Legend extends Element<Legend> {
  @Default
  default Tag tag() {
    return Tag.legend;
  }
}
