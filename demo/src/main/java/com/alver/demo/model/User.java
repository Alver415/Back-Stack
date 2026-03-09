package com.alver.demo.model;

import com.alver.core.model.Entity;
import com.alver.core.util.FieldInfo;
import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.alver.core.util.Invariant.check;
import static org.immutables.value.Value.Check;
import static org.immutables.value.Value.Derived;

@Immutable
@JsonSerialize(as = UserImpl.class)
public interface User extends Entity {
	
	@FieldInfo(order = 0)
	String firstName();
	
	@FieldInfo(order = 1)
	String lastName();
	
	@Derived
	@FieldInfo(order = 2, tooltip = "Derived from First Name and Last Name")
	default String fullName() {
		return String.join(" ", firstName(), lastName());
	}
	
	@FieldInfo(order = 3)
	String email();
	
	@FieldInfo(order = 4)
	String phone();
	
	@FieldInfo(order = 5)
	Address primaryAddress();
	
	@FieldInfo(order = 6, label = "Alternate Address")
	Optional<Address> secondaryAddress();
	
	@Check
	default void validate() {
		check(id().orElse(0L) >= 0, "id can not be negative: %s", id());
		check(!firstName().isBlank(), "firstName can not be blank");
		check(!lastName().isBlank(), "lastName can not be blank");
		check(
			Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email()),
			"invalid email: %s",
			email());
		check(
			Pattern.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", phone()),
			"invalid phone: %s",
			phone());
	}
}
