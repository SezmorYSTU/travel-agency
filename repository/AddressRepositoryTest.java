package com.travel_agency.repository;

import com.travel_agency.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void save_ShouldPersistAddress() {
        Address address = new Address();
        address.setRegion("Moscow Region");
        address.setCity("Moscow");
        address.setStreet("Lenina");
        address.setHouse("1");

        Address saved = addressRepository.save(address);

        assertNotNull(saved.getCode());
        assertEquals("Moscow", saved.getCity());
    }

    @Test
    void findById_ShouldReturnAddress() {
        Address address = new Address();
        address.setRegion("Test Region");
        address.setCity("Test City");
        address.setStreet("Test Street");
        address.setHouse("10");
        addressRepository.save(address);

        Optional<Address> found = addressRepository.findById(address.getCode());

        assertTrue(found.isPresent());
        assertEquals("Test City", found.get().getCity());
    }
}
