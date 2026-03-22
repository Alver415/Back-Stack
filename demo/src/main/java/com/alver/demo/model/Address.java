package com.alver.demo.model;

import com.alver.core.model.Entity;
import com.alver.core.util.Immutable;
import com.alver.demo.model.AddressFields.*;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = AddressImpl.class)
public interface Address extends
	Entity,
	Street,
	City,
	State,
	ZipCode,
	IsApartment {
}
