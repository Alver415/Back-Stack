package com.alver.web.input;

import com.alver.core.util.Immutable;

@Immutable
	interface BooleanControl extends Control<BooleanControl, Boolean> {
		
		@Override
		default String template() {
			//language=mustache
			return """
				    <input type="checkbox"
				    			 name="{{name}}"
				           {{#value}}checked{{/value}}/>
				""";
		}
	}