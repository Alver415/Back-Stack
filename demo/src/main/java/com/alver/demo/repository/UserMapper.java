package com.alver.demo.repository;

import com.alver.data.mapper.EntityMapper;
import com.alver.demo.model.AddressImpl;
import com.alver.demo.model.USState;
import com.alver.demo.model.User;
import com.alver.demo.model.UserImpl;

import java.util.Map;

public class UserMapper extends EntityMapper<User> {
	
	@Override
	public User map(Map<String, Object> rowData) {
		
		UserImpl.Builder builder = UserImpl.builder()
			.id((Long) rowData.get("user.id"))
			.firstName((String) rowData.get("user.first_name"))
			.lastName((String) rowData.get("user.last_name"))
			.email((String) rowData.get("user.email"))
			.phone((String) rowData.get("user.phone"));
		
		builder.primaryAddress(AddressImpl.builder()
			.id((Long) rowData.get("primary_address.id"))
			.street((String) rowData.get("primary_address.street"))
			.city((String) rowData.get("primary_address.city"))
			.state(USState.valueOf((String) rowData.get("primary_address.state")))
			.zipCode((String) rowData.get("primary_address.zip_code"))
			.isApartment((Boolean) rowData.get("primary_address.is_apartment"))
			.build());
		
		if (rowData.get("secondary_address.id") != null) {
			builder.secondaryAddress(
				AddressImpl.builder()
					.id((Long) rowData.get("secondary_address.id"))
					.street((String) rowData.get("secondary_address.street"))
					.city((String) rowData.get("secondary_address.city"))
					.state(USState.valueOf((String) rowData.get("secondary_address.state")))
					.zipCode((String) rowData.get("secondary_address.zip_code"))
					.isApartment((Boolean) rowData.get("secondary_address.is_apartment"))
					.build());
		}
		
		return builder.build();
	}
}
