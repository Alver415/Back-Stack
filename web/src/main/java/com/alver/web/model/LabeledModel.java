package com.alver.web.model;

import com.alver.core.util.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface LabeledModel<T extends LabeledModel<T>> extends Model<T> {

  @Parameter
  String name();
}
