package com.alver.core.model;

import org.immutables.value.Value;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.alver.core.util.Invariant.check;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface Address extends Entity {

    @Value.Parameter(order = 0)
    Optional<Long> id();

    @Value.Parameter(order = 1)
    String street();

    @Value.Parameter(order = 2)
    String city();

    @Value.Parameter(order = 3)
    USState state();

    @Value.Parameter(order = 4)
    String zipCode();

    @Value.Parameter(order = 5)
    Boolean isApartment();

    @Value.Check
    default void validate() {
        check(Pattern.matches("^\\d{5}(-\\d{4})?$", zipCode()),
                String.format("Invalid zipCode: %s", zipCode()));
    }

    enum USState {
        MA,
        CT,
        VT,
        RI,
    }
}
