package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.style.RGBAColor;

@Immutable
public interface CssColor extends CssDeclaration<RGBAColor> {
  default String property() {
    return "color";
  }

  static CssColor of(int r, int g, int b, int a) {
    return CssColorImpl.of(RGBAColor.of(r, g, b, a));
  }
}
