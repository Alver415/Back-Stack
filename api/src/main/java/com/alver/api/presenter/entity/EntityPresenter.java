package com.alver.api.presenter.entity;

import com.alver.api.presenter.app.HtmlPresenter;
import com.alver.api.presenter.model.*;
import com.alver.app.service.IService;
import com.alver.core.model.Entity;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class EntityPresenter<T extends Entity> {

    protected final IService<T> service;
    protected final HtmlPresenter presenter;
    protected final Mustache template;
    protected final Mustache stringTemplate;
    protected final Mustache longTemplate;
    protected final Mustache enumTemplate;
    protected final Mustache radioTemplate;
    protected final Mustache objectTemplate;

    @Autowired
    public EntityPresenter(
            IService<T> service,
            HtmlPresenter presenter,
            MustacheFactory factory
    ) {
        this.service = service;
        this.presenter = presenter;
        this.template = factory.compile("com/alver/api/presenter/entity/entities.mustache");
        this.stringTemplate = factory.compile("com/alver/api/presenter/field/string-field.mustache");
        this.longTemplate = factory.compile("com/alver/api/presenter/field/long-field.mustache");
        this.enumTemplate = factory.compile("com/alver/api/presenter/field/enum-field.mustache");
        this.radioTemplate = factory.compile("com/alver/api/presenter/field/radio-field.mustache");
        this.objectTemplate = factory.compile("com/alver/api/presenter/entity/entity.mustache");
    }

    @GetMapping(produces = "text/html")
    public ResponseEntity<String> getAllHtml() {
        List<T> entities = service.getAll();
        EntitiesPageModel<T> model = ImmutableEntitiesPageModel.of(entities.stream()
                .map(entity -> buildModel(entity.id().map(String::valueOf).orElse("DRAFT"), entity, List.of()))
                .toList());
        return ResponseEntity.ok(presenter.renderPage(template, model));
    }

    <V, X> Model<?> buildModel(String name, V value, List<V> options) {
        if (value instanceof String stringValue) {
            return ImmutableStringFieldModel.of(name, stringValue)
                    .withRenderTemplate((self, input) -> presenter.render(stringTemplate, self));
        } else if (value instanceof Long longValue) {
            return ImmutableLongFieldModel.of(name, longValue)
                    .withRenderTemplate((self, input) -> presenter.render(longTemplate, self));
        } else if (value instanceof Enum enumValue) {
            //noinspection unchecked
            return ImmutableEnumFieldModel.of(name, enumValue, (List<Enum>) options)
                    .withRenderTemplate((self, input) -> presenter.render(enumTemplate, self));
        } else if (!options.isEmpty()) {
            return ImmutableRadioFieldModel.of(name, value, options.stream().map(String::valueOf)
                            .map(v -> ImmutableRadioOption.builder().id(v).name(v).value(v).label(v).build()).toList())
                    .withRenderTemplate((self, input) -> presenter.render(radioTemplate, self));
        } else if (value instanceof Boolean booleanValue) {
            return ImmutableRadioFieldModel.of(name, booleanValue, Stream.of(true, false).map(String::valueOf)
                            .map(b -> ImmutableRadioOption.builder().id(b).name(b).value(b).label(b).build()).toList())
                    .withRenderTemplate((self, input) -> presenter.render(radioTemplate, self));
        } else if (value.getClass().getSimpleName().startsWith("Immutable")) {
            return ImmutableObjectModel.builder()
                    .name(name)
                    .value(value)
                    .renderTemplate((self, input) -> presenter.render(objectTemplate, self))
                    .children(Arrays.stream(value.getClass().getDeclaredMethods())
                            .filter(method -> !Modifier.isStatic(method.getModifiers()))
                            .filter(method -> Modifier.isPublic(method.getModifiers()))
                            .filter(method -> method.getParameterCount() == 0)
                            .filter(method -> !List.of("toString", "hashCode").contains(method.getName()))
                            .filter(method -> !method.getDeclaringClass().equals(Object.class))
                            .sorted(Comparator.comparing(Method::getName))
                            .map(method -> {
                                boolean isEnum = method.getReturnType().isEnum();
                                List<V> optionList = List.of();
                                if (isEnum) {
                                    optionList = (List<V>) Arrays.stream(method.getReturnType().getEnumConstants()).toList();
                                }
                                return buildModel(method.getName(), safeInvoke(method, value), optionList);
                            })
                            .toList())
                    .build();
        } else {
            return ImmutableObjectModel.of(name, value);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T safeInvoke(Method method, Object source) {
        try {
            return (T) method.invoke(source);
        } catch (Exception e) {
            return (T) e.getMessage();
        }
    }

}
