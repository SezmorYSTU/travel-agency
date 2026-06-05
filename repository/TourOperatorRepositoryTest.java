package com.travel_agency.repository;

import com.travel_agency.entity.TourOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TourOperatorRepositoryTest {

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Test
    void findById_WhenExists_ShouldReturnOperator() {
        Optional<TourOperator> result = tourOperatorRepository.findById(1);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getCompanyName());
    }

    @Test
    void findByEmail_WhenExists_ShouldReturnOperator() {
        Optional<TourOperator> result = tourOperatorRepository.findByEmail("info@pegas.ru");

        assertTrue(result.isPresent());
        assertEquals("info@pegas.ru", result.get().getEmail());
    }

    @Test
    void findByEmail_WhenNotExists_ShouldReturnEmpty() {
        Optional<TourOperator> result = tourOperatorRepository.findByEmail("nonexistent@test.ru");

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistOperator() {
        TourOperator operator = new TourOperator();
        operator.setCompanyName("Test Operator");
        operator.setEmail("test@operator.ru");
        operator.setInn("1234567890");
        operator.setPhone("+74950000000");
        operator.setRating(new BigDecimal("4.5"));

        TourOperator saved = tourOperatorRepository.save(operator);

        assertNotNull(saved.getCode());
        assertEquals("Test Operator", saved.getCompanyName());
    }

    @Test
    void findAll_ShouldReturnList() {
        var operators = tourOperatorRepository.findAll();

        assertNotNull(operators);
    }
}