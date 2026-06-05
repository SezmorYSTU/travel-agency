package com.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.travel_agency.entity.TourOperator;
import java.util.Optional;

@Repository
public interface TourOperatorRepository extends JpaRepository<TourOperator, Integer> {
    Optional<TourOperator> findByEmail(String email);
}
