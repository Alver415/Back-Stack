package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Derived;

import java.util.List;

@Immutable
public interface CssDeclarationBlock extends Component<CssDeclarationBlock> {
	
	List<CssDeclaration<?>> declarations();
	
	@Derived
	@Override
	default String template() {
		//language=Mustache
		return """
			{{#declarations}}
				{{{render}}};
			{{/declarations}}
			""";
	}
	
}
