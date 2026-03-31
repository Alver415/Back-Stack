package com.alver.gen;

import com.alver.core.util.Immutable;
import com.alver.functional.exception.TryConsumer;
import com.alver.functional.exception.TryFunction;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Derived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alver.functional.exception.TryFunction.uncheck;
import static com.alver.functional.exception.TryRunnable.uncheck;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@SuppressWarnings({"unchecked", "rawtypes"})
@Immutable
public interface ApplicationProperties {
	
	 Logger log = LoggerFactory.getLogger(ApplicationProperties.class);
	
	@Derived
	default Map<String, String> environmentVariables() {
		return System.getenv();
	}
	
	@Derived
	default Map<String, String> systemProperties() {
		return System.getProperties().entrySet().stream()
			.collect(Collectors.toMap(
				e -> String.valueOf(e.getKey()),
				e -> String.valueOf(e.getValue())));
	}
	
	@Default
	default List<Path> propertySources() {
		String[] cli = commandLineArgs();
		Map<String, String> map = parse(cli);
		String propertySources = map.getOrDefault("propertySources", "");
		String[] split = propertySources.split(",");
		return stream(split)
			.filter(not(String::isEmpty))
			.map(Path::of)
			.toList();
	}
	
	@Derived
	default Map<String, String> applicationProperties() {
		return propertySources().stream()
			.map(TryFunction.uncheck(ApplicationProperties::parse))
			.map(Map::entrySet)
			.flatMap(Collection::stream)
			.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	
	String[] commandLineArgs();
	
	@Derived
	default Map<String, String> commandLineProperties() {
		return parse(commandLineArgs());
	}
	
	@Derived
	default Map<String, String> resolved() {
		return Stream.of(
				environmentVariables(),
				systemProperties(),
				applicationProperties(),
				commandLineProperties()
			).map(Map::entrySet)
			.flatMap(Collection::stream)
			.collect(Collectors.toMap(
				Entry::getKey,
				Entry::getValue,
				(_, entry) -> entry
			));
	}
	
	static <T> T bind(Class<T> type, String... commandLineArgs) throws Exception {
		return bind(type, ApplicationPropertiesImpl.builder()
			.commandLineArgs(commandLineArgs)
			.build().resolved());
	}
	
	static <T> T bind(Class<T> type, Map<String, String> args) throws Exception {
		Object builder = Class.forName("%s.%sImpl".formatted(type.getPackageName(), type.getSimpleName()))
			.getMethod("builder")
			.invoke(null);
		
		args.forEach((String key, String value) -> uncheck(() ->
			ofNullable(findSetters(builder.getClass()))
				.map(s -> s.get(normalize(key)))
				.ifPresentOrElse(TryConsumer.uncheck(s -> s.invoke(builder, convert(value, s.getGenericParameterTypes()[0]))),
					() -> log.atWarn().setMessage("Missing key: %s".formatted(key)).log())
		
		).run());
		
		return type.cast(builder.getClass().getMethod("build").invoke(builder));
	}
	
	static Map<String, String> parse(Path properties) throws IOException {
		return parse(Files.readAllLines(properties));
	}
	
	static Map<String, String> parse(String... args) {
		return parse(Arrays.asList(args));
	}
	
	static Map<String, String> parse(List<String> args) {
		return args.stream()
			.map(arg -> arg.split("=", 2))
			.collect(Collectors.toMap(
				split -> stripPrefix(split[0]),
				split -> split.length > 1 ? split[1] : "true"
			));
	}
	
	private static String stripPrefix(String key) {
		if (key.startsWith("--")) return key.substring(2);
		if (key.startsWith("-")) return key.substring(1);
		return key;
	}
	
	private static String normalize(String input) {
		String[] parts = input.split("-");
		if (parts.length == 1) return input;
		
		return parts[0] + stream(parts, 1, parts.length)
			.map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
			.collect(Collectors.joining());
	}
	
	private static Map<String, Method> findSetters(Class<?> builderClass) {
		return stream(builderClass.getMethods())
			.filter(m -> m.getParameterCount() == 1)
			.filter(m -> !m.getName().equals("from"))
			.collect(Collectors.toMap(Method::getName, m -> m, (a, _) -> a));
	}
	
	private static Object convert(String value, Type type) throws Exception {
		
		if (value == null) return null;
		
		// --- parameterized types (List<T>, Set<T>, Optional<T>) ---
		if (type instanceof ParameterizedType pt) {
			Class<?> raw = (Class<?>) pt.getRawType();
			Type innerType = pt.getActualTypeArguments()[0];
			
			if (raw == List.class) {
				return stream(value.split(","))
					.map(uncheck(v -> convert(v.trim(), innerType)))
					.collect(Collectors.toList());
			}
			
			if (raw == Set.class) {
				return stream(value.split(","))
					.map(uncheck(v -> convert(v.trim(), innerType)))
					.collect(Collectors.toSet());
			}
			
			if (raw == Optional.class) {
				return ofNullable(convert(value, innerType));
			}
		}
		
		// --- raw class ---
		if (!(type instanceof Class<?> clazz)) {
			throw new IllegalArgumentException("Unknown type: %s".formatted(type.getTypeName()));
		}
		
		// String
		if (clazz == String.class) return value;
		
		// primitives + boxed
		if (clazz == int.class || clazz == Integer.class) return Integer.parseInt(value);
		if (clazz == long.class || clazz == Long.class) return Long.parseLong(value);
		if (clazz == double.class || clazz == Double.class) return Double.parseDouble(value);
		if (clazz == float.class || clazz == Float.class) return Float.parseFloat(value);
		if (clazz == short.class || clazz == Short.class) return Short.parseShort(value);
		if (clazz == byte.class || clazz == Byte.class) return Byte.parseByte(value);
		
		if (clazz == boolean.class || clazz == Boolean.class) {
			return switch (value.toLowerCase()) {
				case "true", "1", "yes", "y" -> true;
				case "false", "0", "no", "n" -> false;
				default -> Boolean.parseBoolean(value);
			};
		}
		
		if (clazz == char.class || clazz == Character.class) {
			if (value.length() != 1) throw new IllegalArgumentException("Invalid char: " + value);
			return value.charAt(0);
		}
		
		// numbers
		if (clazz == BigDecimal.class) return new BigDecimal(value);
		if (clazz == BigInteger.class) return new BigInteger(value);
		
		// enums
		if (clazz.isEnum()) return Enum.valueOf((Class<Enum>) clazz, value);
		
		// file / path
		if (clazz == Path.class) return Path.of(value);
		if (clazz == File.class) return new File(value);
		
		// network
		if (clazz == URI.class) return URI.create(value);
		if (clazz == URL.class) return URI.create(value).toURL();
		
		// time
		if (clazz == Instant.class) return Instant.parse(value);
		if (clazz == LocalDate.class) return LocalDate.parse(value);
		if (clazz == LocalDateTime.class) return LocalDateTime.parse(value);
		if (clazz == LocalTime.class) return LocalTime.parse(value);
		if (clazz == Duration.class) return Duration.parse(value);
		
		Method valueOf = clazz.getMethod("valueOf", String.class);
		return isStatic(valueOf.getModifiers()) ?
			valueOf.invoke(null, value) :
			clazz.getConstructor(String.class).newInstance(value);
	}
	
}
