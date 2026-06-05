package com.travel_agency.repository;

import com.travel_agency.entity.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TourRepositoryTest {

    @Autowired
    private TourRepository tourRepository;

    @Test
    void findById_WhenExists_ShouldReturnTour() {
        Optional<Tour> result = tourRepository.findById(1);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getName());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        Optional<Tour> result = tourRepository.findById(9999);

        assertFalse(result.isPresent());
    }

    @Test
    void findByOperator_Code_ShouldReturnTours() {
        List<Tour> tours = tourRepository.findByOperator_Code(1);

        assertNotNull(tours);
        // Все туры должны принадлежать оператору с кодом 1
        tours.forEach(tour -> {
            assertNotNull(tour.getOperator());
            assertEquals(1, tour.getOperator().getCode());
        });
    }

    @Test
    void save_ShouldPersistTour() {
        Tour tour = new Tour();
        tour.setName("Test Tour");
        tour.setCountry("Russia");
        tour.setCity("Moscow");
        tour.setStartDate(LocalDate.now());
        tour.setEndDate(LocalDate.now().plusDays(7));
        tour.setPrice(new BigDecimal("50000"));
        tour.setAvailableSeats(10);

        Tour saved = tourRepository.save(tour);

        assertNotNull(saved.getCode());
        assertEquals("Test Tour", saved.getName());
    }

    @Test
    void findAll_ShouldReturnList() {
        var tours = tourRepository.findAll();

        assertNotNull(tours);
    }

    @Test
    void deleteById_ShouldRemoveTour() {
        Optional<Tour> tour = tourRepository.findById(1);
        assertTrue(tour.isPresent());

        tourRepository.deleteById(1);
        Optional<Tour> deleted = tourRepository.findById(1);

        assertFalse(deleted.isPresent());
    }
}