package com.travel_agency.repository;

import com.travel_agency.entity.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void findById_WhenExists_ShouldReturnBooking() {
        Optional<Booking> result = bookingRepository.findById(1);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getContractNumber());
    }

    @Test
    void save_ShouldPersistBooking() {
        Booking booking = new Booking();
        booking.setContractNumber("TEST-2026-001");
        booking.setBookingDate(LocalDate.now());
        booking.setStatus("Забронировано");
        booking.setTotalAmount(new BigDecimal("75000"));

        Booking saved = bookingRepository.save(booking);

        assertNotNull(saved.getCode());
        assertEquals("TEST-2026-001", saved.getContractNumber());
    }

    @Test
    void findAll_ShouldReturnList() {
        var bookings = bookingRepository.findAll();

        assertNotNull(bookings);
    }
}
