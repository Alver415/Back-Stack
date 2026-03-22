package com.alver.web.html;

import com.alver.core.util.Immutable;
import com.alver.web.component.Component;

import java.util.Optional;

import static org.immutables.value.Value.Default;

public interface Attribute<C extends Attribute<C, T>, T> extends Component<C> {
	
	String name();
	
	Optional<T> value();
	
	static StringAttribute of(String name, String value) {
		return StringAttributeImpl.builder().name(name).value(value).build();
	}
	
	static BooleanAttribute of(String name, Boolean value) {
		return BooleanAttributeImpl.builder().name(name).value(value).build();
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
