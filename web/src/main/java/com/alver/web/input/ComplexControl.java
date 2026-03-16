package com.alver.web.input;

import com.alver.core.model.Entity;
import com.alver.core.util.FieldInfo;
import com.alver.core.util.Immutable;
import com.alver.web.component.FieldImpl;
import com.alver.web.entity.EntityComponentImpl;
import com.alver.web.component.Field;
import org.immutables.value.Value.Default;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Immutable
public interface ComplexControl<T extends ComplexControl<T, V>, V> extends Control<T, V> {
	
	@Override
	default String template() {
		//language=mustache
		return """
			<details open>
				<summary>
					{{name}}
				</summary>
				<div>
					{{#fields}} {{{render}}} {{/fields}}
				</div>
			</details>
			""";
	}
	
	V value();
	
	@Default
	default List<? extends Field<?, ?>> fields() {
		return buildFields(value());
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	static <V> Field<?, V> buildField(String name, String tooltip, V value, List<V> options) {
		return FieldImpl.<Field, V>builder()
			.name(name)
			.label(name)
			.control(buildControl(name, value, options))
			.tooltip(tooltip)
			.build();
	}
	
	static <V> Control<?, V> buildControl(String name, V value) {
		return buildControl(name, value, null);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	static <V> Control<?, V> buildControl(String name, V value, List<V> options) {
		
		return (Control<?, V>) switch (value) {
			case String stringValue -> StringControlImpl.of(name, stringValue);
			case Long longValue -> LongControlImpl.of(name, longValue);
			case Boolean boolValue -> BooleanControlImpl.of(name, boolValue);
			case Optional optional -> buildControl(name, (V) optional.orElse(null), options);
			case Enum enumValue -> SelectControlImpl.of(
				name,
				(V) enumValue,
				(List<V>) Arrays.stream(enumValue.getDeclaringClass().getEnumConstants()).toList()
			);
			case null -> StringControlImpl.of(name, "");
			case Entity entity ->
				EntityComponentImpl.of(entity.id().map("%s"::formatted).orElse(entity.getClass().getSimpleName()), entity);
			default -> ComplexControlImpl.of(name, value);
		};
	}
	
	static <V> List<? extends Field<?, ?>> buildFields(V value) {
		return Arrays.stream(value.getClass().getDeclaredMethods())
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
						optionList = (List<V>) Arrays.stream(method.getReturnType().getEnumConstants()).toList();
					}
					String label = Optional.ofNullable(method.getAnnotation(FieldInfo.class))
						.map(FieldInfo::label)
						.filter(s -> !s.isBlank())
						.orElseGet(() -> camelToNormal(method.getName()));
					
					String tooltip = Optional.ofNullable(method.getAnnotation(FieldInfo.class))
						.map(FieldInfo::tooltip)
						.filter(s -> !s.isBlank())
						.orElse("");
					return (Field<?, ?>)
						buildField(
							label,
							tooltip,
							safeInvoke(method, value),
							optionList);
				}).toList();
	}
	
	@SuppressWarnings("unchecked")
	static <T> T safeInvoke(Method method, Object source) {
		try {
			return (T) method.invoke(source);
		} catch (Exception e) {
			return (T) e.getMessage();
		}
	}
	
	static String camelToNormal(String input) {
		String result = input
			.replaceAll("([a-z])([A-Z])", "$1 $2")
			.replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2");
		
		return Character.toUpperCase(result.charAt(0)) + result.substring(1);
	}
	
}
	