package com.alver.web.component;

import com.alver.core.util.Immutable;

import java.util.List;

import static org.immutables.value.Value.Default;

@Immutable
public interface Parent<T extends Parent<T>> extends Component<T> {
	
	@Default
	default List<Component<?>> children() {
		return List.of();
	}
	
	@Default
	default String template() {
		// language=HTML
		return """
			{{#children}}
			    {{{render}}}
			{{/children}}
			""";
	}
}
