package com.alver.core.model;

import org.immutables.value.Value;

import java.util.Optional;

public interface Entity {
    Long SYSTEM_USER = -1L;

    // Primary Key
    Optional<Long> id();

//    // Database Audit Columns
//    @Value.Default
//    default Long createdBy() {
//        return SYSTEM_USER;
//    }
//
//    @Value.Default
//    default Instant createdAt() {
//        return Instant.now();
//    }
//
//    Optional<Long> updatedBy();
//
//    Optional<Instant> updatedAt();
//
//    Optional<Long> deletedBy();
//
//    Optional<Instant> deletedAt();

}
