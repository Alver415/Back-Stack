package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value;

@Immutable
public interface RGBAColor extends Component<RGBAColor> {

  /** The single packed RGBA value */
  int rgba();

  /** Derived getters */
  @Value.Derived
  default int red() {
    return (rgba() >> 24) & 0xFF;
  }

  @Value.Derived
  default int green() {
    return (rgba() >> 16) & 0xFF;
  }

  @Value.Derived
  default int blue() {
    return (rgba() >> 8) & 0xFF;
  }

  @Value.Derived
  default int alpha() {
    return rgba() & 0xFF;
  }

  @Value.Derived
  default String hex() {
    return String.format("#%08X", rgba()).toUpperCase();
  }

  @Value.Default
  default String templateContent() {
    return hex();
  }

  static RGBAColor of(int r, int g, int b, int a) {
    int packed = ((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8) | (a & 0xFF);
    return RGBAColorImpl.builder().rgba(packed).build();
  }
}
