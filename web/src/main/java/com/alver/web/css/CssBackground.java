package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.style.RGBAColor;

@Immutable
public interface CssBackground extends CssDeclaration<RGBAColor> {
  default String property() {
    return "background";
  }
}
