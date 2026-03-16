package com.alver.web.style;

import com.alver.core.util.Immutable;
import com.alver.web.css.CssDeclaration;
import com.alver.web.html.Attribute;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Immutable
public interface StyleAttribute extends Attribute.StringAttribute {
	@Override
	default String name() {
		return "style";
	}
	
	@Derived
	default Optional<String> value() {
		return Optional.of(cssEntries().stream()
			.map(entry -> entry.renderTemplate(null))
			.collect(Collectors.joining(";")));
	}
	
	@Parameter
	List<CssDeclaration<?>> cssEntries();
}
