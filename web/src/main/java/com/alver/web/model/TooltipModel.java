package com.alver.web.model;

import com.alver.core.util.Immutable;

import java.util.Optional;

@Immutable
public interface TooltipModel<T extends TooltipModel<T>> extends Model<T> {
	
	Optional<String> tooltip();
}
