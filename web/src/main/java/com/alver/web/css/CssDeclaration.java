package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Derived;

@Immutable
public interface CssDeclaration<T> extends Component<CssDeclaration<T>> {
	String property();
	
	T value();
	
	@Derived
	@Override
	default String template() {
		//language=Mustache
		return """
			{{property}}: {{value}}
			""";
	}
	
}
