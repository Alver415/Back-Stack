package com.alver.api.presenter.model;

import org.immutables.value.Value;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
@JsonSerialize(as = ImmutableObjectModel.class)
@JsonDeserialize(as = ImmutableObjectModel.class)
public interface ObjectModel<T extends ObjectModel<T, V>, V> extends ParentModel<T>, LabeledModel<T>, FieldModel<T, V> {
}
