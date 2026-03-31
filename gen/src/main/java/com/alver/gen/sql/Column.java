package com.alver.gen.sql;

import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

import java.sql.JDBCType;

@Immutable
@JsonSerialize(as = ColumnImpl.class)
public interface Column {
	
	String name();
	
	JDBCType type();
	
	boolean nullable();
	
}
