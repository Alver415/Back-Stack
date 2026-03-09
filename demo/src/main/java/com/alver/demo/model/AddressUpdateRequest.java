package com.alver.demo.model;

import com.alver.api.UpdateRequest;
import com.alver.core.util.Immutable;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = AddressUpdateRequestImpl.class)
@JsonDeserialize(as = AddressUpdateRequestImpl.class)
public interface AddressUpdateRequest extends
	UpdateRequest<Address>,
	AddressFields.Street,
	AddressFields.City,
	AddressFields.State,
	AddressFields.ZipCode,
	AddressFields.IsApartment {
}
