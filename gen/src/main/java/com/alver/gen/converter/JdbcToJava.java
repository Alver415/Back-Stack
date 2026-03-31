package com.alver.gen.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.*;

public interface JdbcToJava {
	
	static Class<?> convert(JDBCType jdbcType) {
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
}
