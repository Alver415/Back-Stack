package com.alver.api.presenter.model;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateFunction;
import org.immutables.value.Value;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.io.StringWriter;
import java.util.function.BiFunction;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface Model<T extends Model<T>> {

    @Value.Derived
    default String type() {
        return this.getClass().getSimpleName();
    }

    ObjectWriter JSON = new ObjectMapper().writerWithDefaultPrettyPrinter();

    @Value.Lazy
    default String debug() {
        return JSON.writeValueAsString(this);
    }

    String ERROR_PATH = "com/alver/api/presenter/field/field.mustache";
    Mustache ERROR_TEMPLATE = new DefaultMustacheFactory().compile(ERROR_PATH);

    @Value.Default
    default BiFunction<Model<T>, String, String> renderTemplate() {
        return (self, string) -> {
            StringWriter writer = new StringWriter();
            ERROR_TEMPLATE.execute(writer, self);
            return writer.toString();
        };
    }

    @Value.Derived
    default TemplateFunction render() {
        return (string) -> renderTemplate().apply(Model.this, string);
    }
}
