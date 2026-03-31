package com.alver.gen.model;

import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = FieldImpl.class)
public interface Field<T> {
	
	String name();
	
	Class<T> type();
	
}
