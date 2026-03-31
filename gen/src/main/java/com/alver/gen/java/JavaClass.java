package com.alver.gen.java;

import com.alver.core.util.Immutable;
import com.alver.gen.converter.JdbcToJava;
import com.alver.gen.sql.Column;
import com.alver.gen.sql.Table;
import tools.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alver.gen.converter.Converters.toCamel;
import static com.alver.gen.converter.JdbcToJava.convert;
import static org.immutables.value.Value.Derived;

@Immutable
public interface JavaClass extends Generator<String> {
	
	String javaPackage();
	
	List<Class<?>> imports();
	
	List<Annotation> annotations();
	
	TypeKind kind();
	
	String name();
	
	Table table();
	
	@Derived
	default String fileName() {
		return name() + ".java";
	}
	
	@Derived
	default String annotationsString() {
		return Stream.concat(
				Stream.of(
					"@Immutable",
					"@JsonSerialize(as = {{name}}Impl.class)"
				),
				annotations().stream()
					.map(Annotation::annotationType)
					.map("@%s"::formatted))
			.collect(Collectors.joining("\n"));
	}
	
	
	@Derived
	default String importsString() {
		return Stream.concat(
				Stream.of(
					Optional.class,
					JsonSerialize.class,
					Immutable.class
				),
				Stream.concat(
					imports().stream(),
					table().columns().stream()
						.map(Column::type)
						.map(JdbcToJava::convert)
				))
			.distinct()
			.map(Class::getCanonicalName)
			.map("import %s;"::formatted)
			.collect(Collectors.joining("\n"));
	}
	
	@Derived
	default String fieldsString() {
		return table().columns().stream()
			.map(col -> {
				Class<?> type = convert(col.type());
				String typeString = (col.nullable() ? "Optional<%s>" : "%s")
					.formatted(type.getSimpleName());
				return String.format("\t%s %s();", typeString, toCamel(col.name()));
			}).collect(Collectors.joining("\n"));
	}
	
	@Override
	default String generate() {
		return """
			package {{package}};
			
			{{imports}}
			
			{{annotations}}
			public {{type}} {{name}} {
			
			{{fields}}
			
			}
			"""
			.replace("{{package}}", javaPackage())
			.replace("{{imports}}", importsString())
			.replace("{{annotations}}", annotationsString())
			.replace("{{type}}", kind().text())
			.replace("{{name}}", name())
			.replace("{{fields}}", fieldsString());
	}
	
}
