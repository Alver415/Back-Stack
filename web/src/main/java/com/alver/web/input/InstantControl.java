package com.alver.web.input;

import com.alver.core.util.Immutable;

import java.time.Instant;

@Immutable
interface InstantControl extends Control<InstantControl, Instant> {
	
	@Override
	default String template() {
		//language=mustache
		return """
			//TODO
			""";
	}
}