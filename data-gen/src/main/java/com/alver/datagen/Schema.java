package com.alver.datagen;

import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@Immutable
@JsonSerialize(as = SchemaImpl.class)
public interface Schema {
	
	String name();
	
	List<Table> tables();
}
