package com.alver.web.component;

import com.alver.core.util.Immutable;

import java.util.Optional;

@Immutable
public interface Tooltip<T extends Tooltip<T>> extends Component<T> {
	
	Optional<String> tooltip();
}
