package com.alver.datagen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

public interface Converter {
	static Class<?> mapJdbcTypeToJava(JDBCType jdbcType) {
		return switch (jdbcType) {
			case CHAR, NCHAR, VARCHAR, NVARCHAR, LONGVARCHAR, LONGNVARCHAR, CLOB, NCLOB -> String.class;
			case BIT, BOOLEAN -> Boolean.class;
			case TINYINT -> Byte.class;
			case SMALLINT -> Short.class;
			case INTEGER -> Integer.class;
			case BIGINT -> BigInteger.class;
			case FLOAT, REAL -> Float.class;
			case DOUBLE -> Double.class;
			case DECIMAL, NUMERIC -> BigDecimal.class;
			case DATE -> LocalDate.class;
			case TIME -> LocalTime.class;
			case TIMESTAMP -> Instant.class;
			case TIMESTAMP_WITH_TIMEZONE -> OffsetDateTime.class;
			case TIME_WITH_TIMEZONE -> OffsetTime.class;
			case BINARY, VARBINARY, LONGVARBINARY -> byte[].class;
			case BLOB -> Blob.class;
			case ARRAY -> Array.class;
			case SQLXML -> SQLXML.class;
			case ROWID -> RowId.class;
			case JAVA_OBJECT, OTHER, STRUCT, DISTINCT, DATALINK, REF, REF_CURSOR, NULL -> Object.class;
		};
	}
	
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
	
	static String generateClass(
		String javaPackage,
		List<Type> imports,
		List<Annotation> annotations,
		TypeKind kind,
		String name,
		Table table
	) {
		
		String fields = table.columns().stream()
			.map(col -> {
				Class<?> type = mapJdbcTypeToJava(col.type());
				String typeString = (col.nullable() ? "Optional<%s>" : "%s")
					.formatted(type.getSimpleName());
				return String.format("\t%s %s();", typeString, toCamel(col.name()));
			})
			.collect(Collectors.joining("\n"));
		
		String importString = imports.stream().map("import %s;"::formatted).collect(Collectors.joining("\n"));
		return """
			package %s;
			
			%s
			
			@Immutable
			@JsonSerialize(as = %sImpl.class)
			public %s %s {
			
			%s
			
			}
			""".formatted(javaPackage, importString, name, kind.text(), name, fields);
	}
	
}
