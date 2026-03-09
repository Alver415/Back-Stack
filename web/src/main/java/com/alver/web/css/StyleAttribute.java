package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import com.alver.web.html.Attribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Parameter;

@Immutable
public interface StyleAttribute extends Attribute.StringAttribute {
  @Override
  default String name() {
    return "style";
  }

  @Derived
  default Optional<String> value() {
    return Optional.of(
        cssEntries().stream()
            .map(
                entry -> {
                  var value =
                      entry.value() instanceof Component<?> c
                          ? c.render().apply("")
                          : entry.value();
                  return entry.name() + ": " + value;
                })
            .collect(Collectors.joining("; ")));
  }

  @Parameter
  List<CssEntry<?>> cssEntries();
}
