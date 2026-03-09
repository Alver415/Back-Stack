package com.alver.web.css;

import com.alver.core.util.Immutable;

@Immutable
public interface CssColor extends CssEntry<RGBAColor> {
  default String name() {
    return "color";
  }

  static CssColor of(int r, int g, int b, int a) {
    return CssColorImpl.of(RGBAColor.of(r, g, b, a));
  }
}
