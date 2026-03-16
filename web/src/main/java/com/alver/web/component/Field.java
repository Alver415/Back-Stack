package com.alver.web.component;

import com.alver.core.util.Immutable;
import com.alver.web.input.Control;
import org.immutables.value.Value.Derived;

@Immutable
public interface Field<R extends Field<R, T>, T>
	extends Named<R>, Labeled<R>, Tooltip<R> {
	
	Control<?, T> control();
	
	@Derived
	default T value() {
		return control().value();
	}
	
	@Override
	default String template() {
		//language=mustache
		return """
			<fieldset>
				<legend>
					{{label}} {{#tooltip}}<span title="{{.}}">[?]</span>{{/tooltip}}
				</legend>
				{{#control}}{{{render}}}{{/control}}
			</fieldset>
			""";
	}
}
