package com.alver.web.model;

import com.alver.core.util.Immutable;

@Immutable
public interface ObjectModel<T extends ObjectModel<T, V>, V>
    extends ParentModel<T>, LabeledModel<T>, FieldModel<T, V> {}
