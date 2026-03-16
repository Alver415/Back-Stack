package com.alver.web.html;

import com.alver.core.util.Immutable;

import static org.immutables.value.Value.Default;

@Immutable
public interface FieldSet extends Element<FieldSet> {
  @Default
  default Tag tag() {
    return Tag.fieldSet;
  }
}
