package com.alver.web.component;

import com.alver.core.util.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface Labeled<T extends Labeled<T>> extends Component<T> {

  @Parameter
  String label();
}
