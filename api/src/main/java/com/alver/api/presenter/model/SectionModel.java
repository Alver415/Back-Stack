package com.alver.api.presenter.model;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface SectionModel<T extends SectionModel<T>> extends ParentModel<T>, LabeledModel<T> {
}
