package com.alver.api.presenter.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface LabeledModel<T extends LabeledModel<T>> extends Model<T>{

    @Value.Parameter
    String name();
}
