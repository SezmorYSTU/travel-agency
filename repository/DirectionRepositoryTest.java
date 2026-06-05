package com.travel_agency.repository;

import com.travel_agency.entity.Direction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DirectionRepositoryTest {

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    void save_ShouldPersistDirection() {
        Direction direction = new Direction();
        direction.setName("Пляжный отдых");
        direction.setRestType("Beach");

        Direction saved = directionRepository.save(direction);

        assertNotNull(saved.getCode());
        assertEquals("Пляжный отдых", saved.getName());
    }

    @Test
    void findById_ShouldReturnDirection() {
        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setRestType("Test");
        directionRepository.save(direction);

        Optional<Direction> found = directionRepository.findById(direction.getCode());

        assertTrue(found.isPresent());
        assertEquals("Test Direction", found.get().getName());
    }

    @Test
    void findAll_ShouldReturnList() {
        var directions = directionRepository.findAll();

        assertNotNull(directions);
    }
}