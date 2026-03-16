package com.alver.web.component;

import com.alver.core.util.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface Named<T extends Named<T>> extends Component<T> {

  @Parameter
  String name();
}
