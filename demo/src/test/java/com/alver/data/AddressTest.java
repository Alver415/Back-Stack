package com.alver.data;

import com.alver.demo.model.Address;
import com.alver.demo.model.AddressImpl;
import com.alver.demo.model.USState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {
	
	@Test
	void testAddress() throws InterruptedException {
		
		Address address1 =
			AddressImpl.builder()
				.id(1L)
				.street("123 Main St.")
				.city("Boston")
				.state(USState.MA)
				.zipCode("01234")
				.isApartment(false)
				.build();
		
		Address address2 =
			AddressImpl.builder()
				.id(2L)
				.street("456 Back Rd.")
				.city("Worcester")
				.state(USState.MA)
				.zipCode("43210")
				.isApartment(false)
				.build();
		assertEquals(1L, address1.id().orElseThrow());
		assertEquals(2L, address2.id().orElseThrow());
	}
}
