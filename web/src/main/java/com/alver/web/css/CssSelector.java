package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Derived;

@Immutable
public interface CssSelector extends Component<CssSelector> {
	String selector();
	
	@Derived
	@Override
	default String template() {
		//language=Mustache
		return """
			{{selector}}
			""";
	}
	
}
