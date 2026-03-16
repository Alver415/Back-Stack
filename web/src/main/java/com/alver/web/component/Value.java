package com.alver.web.component;

import static org.immutables.value.Value.Parameter;

import com.alver.core.util.Immutable;

@Immutable
public interface Value<T extends Value<T, V>, V> extends Component<T> {

  @Parameter
  V value();
}
