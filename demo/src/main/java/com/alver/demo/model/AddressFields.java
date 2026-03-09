package com.alver.demo.model;

import org.immutables.value.Value;
import org.immutables.value.Value.Check;

import java.util.regex.Pattern;

import static com.alver.core.util.Invariant.check;

public interface AddressFields {
	
	interface Street {
		String street();
	}
	
	interface City {
		String city();
	}
	
	interface State {
		USState state();
	}
	
	interface ZipCode {
		String zipCode();
		
		@Check
		default void validate() {
			check(
				Pattern.matches("^\\d{5}(-\\d{4})?$", zipCode()),
				String.format("Invalid zipCode: %s", zipCode()));
		}
	}
	
	interface IsApartment {
		Boolean isApartment();
	}
	
	
}
