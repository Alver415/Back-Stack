package com.alver.web.model;

import com.alver.core.util.Immutable;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

import static org.immutables.value.Value.Parameter;

@Immutable
public interface FieldModel<M extends FieldModel<M, V>, V>
	extends LabeledModel<M>, ValueModel<M, V>, TooltipModel<M> {
	
	@Immutable
	interface StringFieldModel extends FieldModel<StringFieldModel, String> {
	}
	
	@Immutable
	interface LongFieldModel extends FieldModel<LongFieldModel, Long> {
	}
	
	@Immutable
	interface BooleanFieldModel extends FieldModel<BooleanFieldModel, Boolean> {
	}
	
	@Immutable
	interface EnumFieldModel<V extends Enum<V>> extends FieldModel<EnumFieldModel<V>, V> {
		@Parameter
		List<V> options();
	}
	
	@Immutable
	interface RadioFieldModel<V> extends FieldModel<RadioFieldModel<V>, V> {
		@Parameter
		List<RadioOption<V>> options();
		
		@Immutable
		interface RadioOption<V> extends Model<RadioOption<V>> {
			String id();
			
			String name();
			
			String label();
			
			String value();
		}
	}
	
	@Immutable
	interface InstantFieldModel extends FieldModel<InstantFieldModel, Instant> {
	}
}
