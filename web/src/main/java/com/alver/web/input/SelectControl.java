package com.alver.web.input;

import com.alver.core.util.Immutable;
import org.immutables.value.Value.Parameter;

import java.util.List;

@Immutable
interface SelectControl<V> extends Control<SelectControl<V>, V> {
	@Parameter
	List<V> options();
	
	@Override
	default String template() {
		//language=mustache
		return """
			    <select id="field-id-{{id}}">
			        {{#options}}
			             <option value="{{.}}">{{.}}</option>
			         {{/options}}
			      </select>
			""";
	}
}
	