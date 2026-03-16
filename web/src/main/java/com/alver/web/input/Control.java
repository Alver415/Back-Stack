package com.alver.web.input;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;

@Immutable
public interface Control<R extends Control<R, T>, T> extends Component<R> {
	
	String name();
	
	T value();
}
