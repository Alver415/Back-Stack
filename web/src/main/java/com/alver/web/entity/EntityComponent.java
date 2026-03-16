package com.alver.web.entity;

import com.alver.core.model.Entity;
import com.alver.core.util.Immutable;
import com.alver.web.input.ComplexControl;
import org.immutables.value.Value.Derived;

import static org.immutables.value.Value.Default;

@Immutable
public interface EntityComponent<R, T extends Entity> extends ComplexControl<EntityComponent<R, T>, T> {
	
	@Derived
	default String type(){
		return getClass().getSimpleName() + Math.random();
	}
	
	@Override
	@Default
	default String template() {
		//language=Mustache
		return """
			<details open>
				<summary>{{name}}</summary>
			
				<section>
					{{#fields}} {{{render}}} {{/fields}}
				</section>
			
				<form id="form-{{type}}-{{id}}" method='post'>
					<button onclick='submit' formaction='/delete'>Delete</button>
					<button onclick='submit' formaction='/update'>Update</button>
				</form>
			</details>
			""";
	}
}