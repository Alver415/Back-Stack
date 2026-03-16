package com.alver.web.component;

import com.alver.core.util.Immutable;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.TemplateFunction;
import org.immutables.value.Value;
import org.immutables.value.Value.Lazy;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.io.StringReader;
import java.io.StringWriter;

import static org.immutables.value.Value.Default;

@Immutable
public interface Component<T extends Component<T>> {
	DefaultMustacheFactory FACTORY = new DefaultMustacheFactory();
	
	@Default
	default String template() {
		return debugTemplate();
	}
	
	@Default
	default Mustache mustache() {
		StringReader reader = new StringReader(this.template());
		String name = getClass().getSimpleName();
		return FACTORY.compile(reader, name);
	}
	
	default TemplateFunction render() {
		return this::renderTemplate;
	}
	
	
	default String renderTemplate() {
		return renderTemplate(null);
	}
	
	default String renderTemplate(String string) {
		StringWriter writer = new StringWriter();
		mustache().execute(writer, this);
		return writer.toString();
	}
	
	
	default String debugTemplate() {
		//language=Mustache
		return """
         <details>
             <summary>
               <code>
                 Debug
               </code>
             </summary>
             <!-- {debug} is derived on the base Model interface which returns a prettified json representation. -->
             <pre>
               <code>
                 {{#debug}}{{debug}}{{/debug}}
                 {{^debug}}{{.}}{{/debug}}
               </code>
             </pre>
         </details>
    """;
	}
	
	default String debug(){
		ObjectWriter JSON = new ObjectMapper().writerWithDefaultPrettyPrinter();
		return JSON.writeValueAsString(JSON);
	}
	
}
