package com.alver.core.model;

import org.immutables.value.Value;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.alver.core.util.Invariant.check;

@Value.Immutable
@Value.Style(jacksonIntegration = true)
public interface User extends Entity {

    String firstName();

    String lastName();

    // Derived field: '{firstName} {lastName}'
    default String fullName() {
        return String.join(" ", firstName(), lastName());
    }

    String email();

    String phone();

    Address primaryAddress();

    Optional<Address> secondaryAddress();

    @Value.Check
    default void validate() {
        check(id().orElse(0L) >= 0,
                "id can not be negative: %s", id());
//        check(createdBy() >= 0,
//                "createdBy can not be negative: %s", createdBy());
//        check(createdAt().isAfter(Instant.EPOCH),
//                "createdAt can not be before the epoch: %s", createdAt());
        check(!firstName().isBlank(),
                "firstName can not be blank");
        check(!firstName().isBlank(),
                "lastName can not be blank");
        check(Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email()),
                "invalid email: %s", email());
        check(Pattern.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", phone()),
                "invalid phone: %s", phone());
    }
}
