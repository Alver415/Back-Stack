package com.alver.web.component;

import com.alver.core.util.Immutable;

@Immutable
public interface Section<T extends Section<T>> extends Parent<T>, Labeled<T> {}
