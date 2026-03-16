package com.alver.web.input;

import com.alver.core.util.Immutable;

@Immutable
interface LongControl extends Control<LongControl, Long> {
	
	@Override
	default String template() {
		//language=mustache
		return """
			    <input type="number"
			           name="{{name}}"
			           value="{{value}}"/>
			""";
	}
}