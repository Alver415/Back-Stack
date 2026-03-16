package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Derived;

import java.util.List;
import java.util.stream.Collectors;

@Immutable
public interface CssRule extends Component<CssRule> {
	
	@Default
	default List<CssSelector> selectorList() {
		return List.of(CssSelectorImpl.of("*"));
	}
	
	@Derived
	default String selectors() {
		return selectorList().stream().map(Component::renderTemplate).collect(Collectors.joining(", "));
	}
	
	CssDeclarationBlock declarationBlock();
	
	@Derived
	@Override
	default String template() {
		//language=Mustache
		return """
			{{{selectors}}} {
			 {{{declarationBlock.render}}}
			}
			""";
	}
	
}
