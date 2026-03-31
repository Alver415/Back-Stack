package com.alver.gen.sql;

import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@Immutable
@JsonSerialize(as = CatalogImpl.class)
public interface Catalog {
	
	String name();
	
	List<Schema> schemas();
}
