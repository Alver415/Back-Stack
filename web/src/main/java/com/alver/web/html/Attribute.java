package com.alver.web.html;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;

import java.util.Optional;

import static org.immutables.value.Value.Default;
import static org.immutables.value.Value.Parameter;

public interface Attribute<C extends Attribute<C, T>, T> extends Component<C> {
	
	@Parameter
	String name();
	
	@Parameter
	Optional<T> value();
	
	static StringAttribute of(String name, String value) {
		return StringAttributeImpl.of(name, Optional.ofNullable(value));
	}
	
	static BooleanAttribute of(String name, Boolean value) {
		return BooleanAttributeImpl.of(name, Optional.ofNullable(value));
	}
	
	
	@Immutable
	interface StringAttribute extends Attribute<StringAttribute, String> {
		@Default
		default String template() {
			return "{{name}}=\"{{value}}\"";
		}
	}
	
	@Immutable
	interface BooleanAttribute extends Attribute<BooleanAttribute, Boolean> {
		@Default
		default String template() {
			return value().orElse(false) ? "{{name}}" : "";
		}
	}
}
