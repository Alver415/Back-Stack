package com.alver.gen.converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public interface Converters {
	
	static String toCamel(String s) {
		String[] parts = s.toLowerCase().split("_");
		StringBuilder sb = new StringBuilder(parts[0]);
		
		for (int i = 1; i < parts.length; i++) {
			sb.append(Character.toUpperCase(parts[i].charAt(0)))
				.append(parts[i].substring(1));
		}
		
		return sb.toString();
	}
	
	static String toClassName(String table) {
		String camel = toCamel(table);
		return Character.toUpperCase(camel.charAt(0)) + camel.substring(1);
	}
	
	Set<String> JAVA_KEYWORDS = Set.of(
		"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
		"const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
		"finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
		"interface", "long", "native", "new", "package", "private", "protected", "public",
		"return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
		"throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false", "null"
	);
	
	static String toJavaPackage(String input) {
		return Arrays.stream(input
				.toLowerCase()
				.replaceAll("[^a-z0-9]+", ".")
				.split("\\."))
			.filter(not(String::isBlank))
			.map(segment -> {
				if (Character.isDigit(segment.charAt(0))) {
					segment = "_" + segment;
				}
				if (JAVA_KEYWORDS.contains(segment)) {
					segment = "_%s_".formatted(segment);
				}
				return segment;
			}).collect(Collectors.joining("."));
	}
	
}
