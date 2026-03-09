package com.alver.web.model;

import static org.immutables.value.Value.Derived;
import static org.immutables.value.Value.Lazy;

import com.alver.core.util.Immutable;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateFunction;
import java.io.StringWriter;
import java.util.function.BiFunction;
import org.immutables.value.Value.Default;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

@Immutable
public interface Model<T extends Model<T>> {

  @Derived
  default String type() {
    return this.getClass().getSimpleName();
  }

  ObjectWriter JSON = new ObjectMapper().writerWithDefaultPrettyPrinter();

  @Lazy
  default String debug() {
    return JSON.writeValueAsString(this);
  }

  String ERROR_PATH = "com/alver/web/field/field.mustache";
  Mustache ERROR_TEMPLATE = new DefaultMustacheFactory().compile(ERROR_PATH);

  @Default
  default BiFunction<Model<T>, String, String> renderTemplate() {
    return (self, _) -> {
      StringWriter writer = new StringWriter();
      ERROR_TEMPLATE.execute(writer, self);
      return writer.toString();
    };
  }

  @Derived
  default TemplateFunction render() {
    return (string) -> renderTemplate().apply(this, string);
  }
}
