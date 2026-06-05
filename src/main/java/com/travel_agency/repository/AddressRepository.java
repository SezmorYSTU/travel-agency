package com.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.travel_agency.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}