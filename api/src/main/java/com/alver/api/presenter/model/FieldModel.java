package com.alver.api.presenter.model;

import org.immutables.value.Value;

import java.time.Instant;
import java.util.List;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface FieldModel<T extends FieldModel<T, V>, V> extends LabeledModel<T>, ValueModel<T, V> {

    static StringFieldModel ofString(String name, String value) {
        return ImmutableStringFieldModel.of(name, value);
    }

    static LongFieldModel ofLong(String name, Long value) {
        return ImmutableLongFieldModel.of(name, value);
    }

    static InstantFieldModel ofInstant(String name, Instant value) {
        return ImmutableInstantFieldModel.of(name, value);
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface StringFieldModel extends FieldModel<StringFieldModel, String> {
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface LongFieldModel extends FieldModel<LongFieldModel, Long> {
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface EnumFieldModel<T extends Enum<T>> extends FieldModel<EnumFieldModel<T>, Enum<T>> {
        @Value.Parameter
        List<T> options();
    }


    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface RadioFieldModel<T> extends FieldModel<RadioFieldModel<T>, T> {
        @Value.Parameter
        List<RadioOption<T>> options();

        @Value.Immutable
@Value.Style(jacksonIntegration = true)
        interface RadioOption<T> extends Model<RadioOption<T>>{
            String id();
            String name();
            String label();
            String value();
        }
    }

    @Value.Immutable
@Value.Style(jacksonIntegration = true)
    interface InstantFieldModel extends FieldModel<InstantFieldModel, Instant> {
    }
}
