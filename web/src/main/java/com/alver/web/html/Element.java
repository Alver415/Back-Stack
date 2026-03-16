package com.alver.web.html;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import com.alver.web.component.Parent;

import java.util.List;

import static org.immutables.value.Value.Default;

@Immutable
public interface Element<T extends Element<T>> extends Component<T>, Parent<T> {
	
	Tag tag();
	
	@Default
	default List<Attribute<?, ?>> attributes() {
		return List.of();
	}
	
	@Default
	default String template() {
		// language=HTML
		return """
			<{{tag}} {{#attributes}}{{{render}}}{{/attributes}}>
			    {{#children}}
			        {{{render}}}
			    {{/children}}
			</{{tag}}>
			""";
	}
	
	enum Tag {
		div,
		span,
		label,
		input,
		fieldSet,
		legend;
	}
}
