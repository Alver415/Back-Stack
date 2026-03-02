package com.alver.data;

import com.alver.core.model.Address;
import com.alver.core.model.ImmutableAddress;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressTest {

    @Test
    void testAddress() throws InterruptedException {

        Address address1 = ImmutableAddress.builder()
                .id(1L)
                .street("123 Main St.")
                .city("Boston")
                .state(Address.USState.MA)
                .zipCode("01234")
                .isApartment(false)
                .build();

        Address address2 = ImmutableAddress.of(
                Optional.of(2L),
                "456 Back Rd.",
                "Worcester",
                Address.USState.MA,
                "43210",
                false);

        assertEquals("1", address1.id());
        assertEquals("2", address2.id());
//        assertNotEquals(address1.createdAt(), address2.createdAt());

        // createdAt() returns Instant.now(), but its computed once at creation.
//        Instant first = address1.createdAt();
//        Thread.sleep(1000);
//        Instant second = address1.createdAt();
//        assertEquals(first, second);
    }
}
