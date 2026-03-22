package com.alver.datagen.generated;

import java.util.Optional;
import tools.jackson.databind.annotation.JsonSerialize;
import com.alver.core.util.Immutable;
import java.math.BigInteger;
import java.lang.String;
import java.time.Instant;

@Immutable
@JsonSerialize(as = UserImpl.class)

public interface User {

	BigInteger id();
	String firstName();
	String lastName();
	String email();
	Optional<String> phone();
	BigInteger primaryAddressId();
	Optional<BigInteger> secondaryAddressId();
	Instant createdAt();
	BigInteger createdBy();
	Optional<Instant> updatedAt();
	Optional<BigInteger> updatedBy();
	Optional<Instant> deletedAt();
	Optional<BigInteger> deletedBy();

}
