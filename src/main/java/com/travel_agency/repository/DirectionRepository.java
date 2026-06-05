package com.travel_agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.travel_agency.entity.Direction;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Integer> {
    // Пустой интерфейс, но Spring Data JPA автоматически реализует:
    // findAll()  SELECT * FROM "Направление"
    // findById(id)  SELECT * FROM "Направление" WHERE "Код_направления" = ?
    // save(entity)  INSERT или UPDATE
    // deleteById(id)  DELETE FROM "Направление" WHERE ...
    // count(), existsById() и др.
}
