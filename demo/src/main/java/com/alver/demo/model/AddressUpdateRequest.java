package com.alver.demo.model;

import com.alver.api.UpdateRequest;
import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import static com.alver.demo.model.AddressFields.*;

@Immutable
@JsonSerialize(as = AddressUpdateRequestImpl.class)
@JsonDeserialize(as = AddressUpdateRequestImpl.class)
public interface AddressUpdateRequest extends UpdateRequest<Address>,
	Street,
	City,
	State,
	ZipCode,
	IsApartment {
}
