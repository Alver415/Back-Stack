package com.alver.api.presenter.entity;

import com.alver.core.model.Entity;
import com.alver.api.presenter.model.Model;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface EntityModel<T extends Entity> extends Model {

    @Value.Parameter
    T entity();
}
