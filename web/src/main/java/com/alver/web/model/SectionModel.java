package com.alver.web.model;

import com.alver.core.util.Immutable;

@Immutable
public interface SectionModel<T extends SectionModel<T>> extends ParentModel<T>, LabeledModel<T> {}
