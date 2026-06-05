package com.travel_agency.repository;

import com.travel_agency.entity.Passport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassportRepositoryTest {

    @Autowired
    private PassportRepository passportRepository;

    @Test
    void save_ShouldPersistPassport() {
        Passport passport = new Passport();
        passport.setSeries("1234");
        passport.setNumber("567890");
        passport.setIssuedBy("MVD");
        passport.setIssueDate(LocalDate.now());

        Passport saved = passportRepository.save(passport);

        assertNotNull(saved.getCode());
        assertEquals("1234", saved.getSeries());
    }

    @Test
    void findById_ShouldReturnPassport() {
        Passport passport = new Passport();
        passport.setSeries("4321");
        passport.setNumber("098765");
        passport.setIssuedBy("Test MVD");
        passport.setIssueDate(LocalDate.now());
        passportRepository.save(passport);

        Optional<Passport> found = passportRepository.findById(passport.getCode());

        assertTrue(found.isPresent());
        assertEquals("4321", found.get().getSeries());
    }
}