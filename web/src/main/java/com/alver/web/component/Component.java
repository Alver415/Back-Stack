package com.alver.web.component;

import static org.immutables.value.Value.Default;
import static org.immutables.value.Value.Derived;

import com.alver.core.util.Immutable;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateFunction;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.BiFunction;

@Immutable
public interface Component<T extends Component<T>> {
  DefaultMustacheFactory factory = new DefaultMustacheFactory();

  @Default
  default String templateContent() {
    return "[NOT IMPLEMENTED]";
  }

  @Default
  default String templateName() {
    return "[NOT IMPLEMENTED]";
  }

  @Default
  default Mustache template() {
    return factory.compile(new StringReader(templateContent()), templateName());
  }

  /** This is the method that can be used to override the standard rendering template. */
  @Default
  default BiFunction<Component<T>, String, String> renderTemplate() {
    return (self, _) -> {
      StringWriter writer = new StringWriter();
      template().execute(writer, self);
      return writer.toString();
    };
  }

  /**
   * This is the method that mustache actually calls. This component uses the component's
   * renderTemplate field and passes itself as the model for rendering.
   */
  @Derived
  default TemplateFunction render() {
    return (string) -> renderTemplate().apply(Component.this, string);
  }
}
