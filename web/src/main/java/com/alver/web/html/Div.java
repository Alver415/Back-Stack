package com.alver.web.html;

import static org.immutables.value.Value.Default;

import com.alver.core.util.Immutable;

@Immutable
public interface Div extends Element<Div> {
  @Default
  default Tag tag() {
    return Tag.div;
  }
}
