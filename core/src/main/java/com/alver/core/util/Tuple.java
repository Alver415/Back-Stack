package com.alver.core.util;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface Tuple<A, B> {
    @Value.Parameter
    Optional<A> a();

    @Value.Parameter
    Optional<B> b();

}