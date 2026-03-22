package com.alver.datagen.generated;

import java.util.Optional;
import tools.jackson.databind.annotation.JsonSerialize;
import com.alver.core.util.Immutable;
import java.math.BigInteger;
import java.lang.String;
import java.lang.Boolean;
import java.time.Instant;

@Immutable
@JsonSerialize(as = AddressImpl.class)

public interface Address {

	BigInteger id();
	String street();
	String city();
	String state();
	String zipCode();
	Optional<Boolean> isApartment();
	Instant createdAt();
	BigInteger createdBy();
	Optional<Instant> updatedAt();
	Optional<BigInteger> updatedBy();
	Optional<Instant> deletedAt();
	Optional<BigInteger> deletedBy();

}
