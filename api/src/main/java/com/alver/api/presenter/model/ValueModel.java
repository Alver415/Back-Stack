package com.alver.api.presenter.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface ValueModel<T extends ValueModel<T, V>, V> extends Model<T> {

    @Value.Parameter
    V value();

}
