package com.alver.demo.model;

import com.alver.api.CreateRequest;
import com.alver.core.util.Immutable;
import com.alver.demo.model.AddressFields.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = AddressCreateRequestImpl.class)
@JsonDeserialize(as = AddressCreateRequestImpl.class)
public interface AddressCreateRequest extends CreateRequest<Address>,
	Street,
	City,
	State,
	ZipCode,
	IsApartment {
}
