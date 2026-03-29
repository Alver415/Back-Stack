package com.alver.functional.named;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.ofNullable;

public interface NamedFunction<T extends Record, R> extends Function<T, R> {
	
	Class<T> parameterType();
	
	@Override
	R apply(T t);
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	default R apply(Object... args) {
		if (args.length == 1) {
			if (parameterType().isInstance(args[0])) {
				return apply((T) args[0]);
			} else if (args[0] instanceof Map map) {
				return apply((Map<String, Object>) map);
			}
		}
		return apply(IntStream.range(0, args.length)
			.mapToObj(i -> args[i] instanceof NamedArg<?> namedArg
				? namedArg
				: NamedArg.arg("arg" + i, args[i])
			).toArray(NamedArg<?>[]::new)
		);
	}
	
	default R apply(Map<String, Object> parameters) {
		return apply(parameters.entrySet().stream()
			.map(NamedArg::from)
			.toArray(length -> new NamedArg<?>[length]));
	}
	
	default R apply(NamedArg<?>... parameters) {
		try {
			return apply(buildParameters(parameterType(), parameters));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static <T extends Record> T buildParameters(Class<T> parameterType, NamedArg<?>... args)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		Map<String, Object> map = Arrays.stream(args).collect(Collectors.toMap(
			NamedArg::name,
			NamedArg::value,
			(_, arg) -> arg // In the case of duplicate keys, last arg wins.
		));
		
		RecordComponent[] components = parameterType.getRecordComponents();
		Constructor<T> constructor = parameterType.getConstructor(Arrays.stream(components)
			.map(RecordComponent::getType)
			.toArray(length -> new Class<?>[length]));
		
		Object[] values = IntStream.range(0, components.length)
			.mapToObj(i -> ofNullable(map.remove(components[i].getName()))
				.orElse(i < args.length ? args[i].value() : null))
			.toArray(Object[]::new);
		
		return constructor.newInstance(values);
	}
	
	static <T extends Record, R> NamedFunction<T, R> of(Class<T> argType, Function<T, R> function) {
		return new NamedFunction<>() {
			@Override
			public Class<T> parameterType() {
				return argType;
			}
			
			@Override
			public R apply(T arg) {
				return function.apply(arg);
			}
		};
	}
}
