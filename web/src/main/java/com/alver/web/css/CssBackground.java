package com.alver.web.css;

import com.alver.core.util.Immutable;

@Immutable
public interface CssBackground extends CssEntry<RGBAColor> {
  default String name() {
    return "background";
  }
}
