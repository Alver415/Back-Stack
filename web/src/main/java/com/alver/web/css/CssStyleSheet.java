package com.alver.web.css;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import org.immutables.value.Value.Default;

import java.util.List;

@Immutable
public interface CssStyleSheet extends Component<CssStyleSheet> {
	
	List<CssRule> ruleList();
	
	@Override
	@Default
	default String template() {
		//language=Mustache
		return """
			{{#ruleList}}
				{{{render}}}
			{{/ruleList}}
			""";
	}
}
