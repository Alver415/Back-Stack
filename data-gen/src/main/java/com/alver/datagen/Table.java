package com.alver.datagen;

import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@Immutable
@JsonSerialize(as = TableImpl.class)
public interface Table {
	
	String name();
	
	List<Column> columns();
}
