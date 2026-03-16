package com.alver.web.input;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Parameter;

import java.util.List;

@Immutable
interface RadioControl<V> extends Control<RadioControl<V>, V> {
	@Parameter
	List<Option<V>> options();
	
	@Immutable
	interface Option<V> extends Component<Option<V>> {
		String id();
		
		String name();
		
		String label();
		
		String value();
	}
	
	@Override
	default String template() {
		//language=mustache
		return """
			{{#options}}
				<input type="radio" id="{{id}}" name="{{name}}" value="{{value}}">
				<label for="{{id}}">{{name}}</label>
				<br/>
			{{/options}}
			""";
	}
}