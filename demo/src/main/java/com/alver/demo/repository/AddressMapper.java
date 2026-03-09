package com.alver.demo.repository;

import com.alver.data.mapper.EntityMapper;
import com.alver.demo.model.Address;
import com.alver.demo.model.AddressImpl;
import com.alver.demo.model.USState;

import java.util.Map;

public class AddressMapper extends EntityMapper<Address> {
	
	@Override
	public Address map(Map<String, Object> rowData) {
		return AddressImpl.builder()
			.id((Long) rowData.get("address.id"))
			.street((String) rowData.get("address.street"))
			.city((String) rowData.get("address.city"))
			.state(USState.valueOf((String) rowData.get("address.state")))
			.zipCode((String) rowData.get("address.zip_code"))
			.isApartment((Boolean) rowData.get("address.is_apartment"))
			.build();
	}
}
