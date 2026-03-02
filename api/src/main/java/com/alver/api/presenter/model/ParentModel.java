package com.alver.api.presenter.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface ParentModel<T extends ParentModel<T>> extends Model<T> {

    @Value.Default
    default List<Model<?>> children() {
        return List.of();
    }

}
