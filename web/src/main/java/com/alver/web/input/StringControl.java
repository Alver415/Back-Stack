package com.alver.web.input;

import com.alver.core.util.Immutable;

@Immutable
interface StringControl extends Control<StringControl, String> {
	
	@Override
	default String template() {
		//language=mustache
		return """
			    <input type="text"
			           name="{{name}}"
			           value="{{value}}"/>
			""";
	}
}
	