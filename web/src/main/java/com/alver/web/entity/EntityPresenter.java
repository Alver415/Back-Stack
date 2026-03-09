package com.alver.web.entity;

import com.alver.app.service.Service;
import com.alver.core.model.Entity;
import com.alver.core.util.FieldInfo;
import com.alver.web.app.HtmlPresenter;
import com.alver.web.model.*;
import com.alver.web.presenter.Presenter;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EntityPresenter<T extends Entity> implements Presenter {
	
	protected final Service<T> service;
	protected final HtmlPresenter presenter;
	protected final Mustache entitiesTemplate;
	protected final Mustache stringTemplate;
	protected final Mustache longTemplate;
	protected final Mustache booleanTemplate;
	protected final Mustache enumTemplate;
	protected final Mustache radioTemplate;
	protected final Mustache objectTemplate;
	
	public EntityPresenter(Service<T> service, HtmlPresenter presenter, MustacheFactory factory) {
		this.service = service;
		this.presenter = presenter;
		this.entitiesTemplate = factory.compile("com/alver/web/entity/entities.mustache");
		this.stringTemplate = factory.compile("com/alver/web/field/string-field.mustache");
		this.longTemplate = factory.compile("com/alver/web/field/long-field.mustache");
		this.booleanTemplate = factory.compile("com/alver/web/field/boolean-field.mustache");
		this.enumTemplate = factory.compile("com/alver/web/field/enum-field.mustache");
		this.radioTemplate = factory.compile("com/alver/web/field/radio-field.mustache");
		this.objectTemplate = factory.compile("com/alver/web/entity/entity.mustache");
	}
	
	@Override
	public String render() {
		List<T> entities = service.getAll();
		EntitiesPageModel<T> model = EntitiesPageModelImpl.of(entities.stream()
			.map(entity -> buildModel(entity.id().map(String::valueOf).orElse("DRAFT"), "", entity, List.of()))
			.toList());
		return presenter.renderPage(entitiesTemplate, model);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	<V> Model<?> buildModel(String name, String tooltip, V value, List<V> options) {
		if (value == null) {
			return FieldModelImpl.builder().name(name).value("null").build();
		} else if (value instanceof Optional optional) {
			return buildModel(name, tooltip, (V) optional.orElse(null), options);
		} else if (value instanceof String stringValue) {
			return StringFieldModelImpl.of(name, stringValue).withTooltip(tooltip)
				.withRenderTemplate((self, _) -> presenter.render(stringTemplate, self));
		} else if (value instanceof Long longValue) {
			return LongFieldModelImpl.of(name, longValue)
				.withRenderTemplate((self, _) -> presenter.render(longTemplate, self));
		} else if (value instanceof Boolean booleanValue) {
			return BooleanFieldModelImpl.of(name, booleanValue)
				.withRenderTemplate((self, _) -> presenter.render(booleanTemplate, self));
		} else if (value instanceof Enum enumValue) {
			return EnumFieldModelImpl.of(name, enumValue, (List<Enum>) options)
				.withRenderTemplate((self, _) -> presenter.render(enumTemplate, self));
		} else if (!options.isEmpty()) {
			List<RadioOptionImpl<Object>> optionList = options.stream()
				.map(String::valueOf)
				.map(v -> RadioOptionImpl.builder()
					.id(v).name(v).label(v).value(v).build())
				.toList();
			return RadioFieldModelImpl.of(name, value, optionList)
				.withRenderTemplate((self, _) -> presenter.render(radioTemplate, self));
		} else if (value.getClass().getPackageName().startsWith("com.alver")) {
			return ObjectModelImpl.of(name, value)
				.withRenderTemplate((self, _) -> presenter.render(objectTemplate, self))
				.withChildren(
					Arrays.stream(value.getClass().getDeclaredMethods())
						.filter(method -> !Modifier.isStatic(method.getModifiers()))
						.filter(method -> Modifier.isPublic(method.getModifiers()))
						.filter(method -> method.getParameterCount() == 0)
						.filter(method -> !List.of("toString", "hashCode").contains(method.getName()))
						.filter(method -> !method.getDeclaringClass().equals(Object.class))
						.sorted(Comparator.comparing(Method::getName))
						.map(
							method -> {
								boolean isEnum = method.getReturnType().isEnum();
								List<V> optionList = List.of();
								if (isEnum) {
									//noinspection unchecked
									optionList =
										(List<V>)
											Arrays.stream(method.getReturnType().getEnumConstants()).toList();
								}
								String label = Optional.ofNullable(method.getAnnotation(FieldInfo.class))
									.map(FieldInfo::label)
									.filter(s -> !s.isBlank())
									.orElseGet(() -> camelToNormal(method.getName()));
								
								String tooltip2 = Optional.ofNullable(method.getAnnotation(FieldInfo.class))
									.map(FieldInfo::tooltip)
									.filter(s -> !s.isBlank())
									.orElse("");
								return (Model<?>)
									buildModel(
										label,
										tooltip2,
										safeInvoke(method, value),
										optionList
									);
							})
						.toList());
		} else {
			return ObjectModelImpl.of(name, value);
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
	
	public static String camelToNormal(String input) {
		String result = input
			.replaceAll("([a-z])([A-Z])", "$1 $2")
			.replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2");
		
		return Character.toUpperCase(result.charAt(0)) + result.substring(1);
	}
	
}
