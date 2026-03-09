package com.alver.demo.model;

import com.alver.core.model.Entity;
import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = AddressImpl.class)
public interface Address extends
	Entity,
	AddressFields.Street,
	AddressFields.City,
	AddressFields.State,
	AddressFields.ZipCode,
	AddressFields.IsApartment {
}
