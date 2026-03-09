package com.alver.web.component;

import static org.immutables.value.Value.Default;

import com.alver.core.util.Immutable;
import java.util.List;

@Immutable
public interface Parent<T extends Parent<T>> extends Component<T> {

  @Default
  default List<Component<?>> children() {
    return List.of();
  }

  @Default
  default String templateContent() {
    // language=HTML
    return """
    {{#children}}
        {{{render}}}
    {{/children}}
    """;
  }
}
