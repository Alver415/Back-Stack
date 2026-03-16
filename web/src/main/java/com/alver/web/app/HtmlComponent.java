package com.alver.web.app;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;
import com.alver.web.css.CssStyleSheet;
import org.immutables.value.Value;

@Immutable
public interface HtmlComponent extends Component<HtmlComponent> {
	
	String title();
	
	String version();
	
	CssStyleSheet styleSheet();
	
	Component<?> content();
	
	@Override
	@Value.Default
	default String template() {
		//language=Mustache
		return """
			<!DOCTYPE html>
			<html lang="en">
			<head>
			    <meta charset="UTF-8">
			    <title>{{title}} {{#title}}- {{.}}{{/title}}</title>
			    <style>{{{styleSheet.render}}}</style>
			</head>
			<body>
			<main>
			    {{{content.render}}}
			</main>
			</body>
			</html>
			""";
	}
}
