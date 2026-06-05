package com.travel_agency.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void testBookingGettersAndSetters() {
        Booking booking = new Booking();
        booking.setCode(101);
        booking.setContractNumber("BR-2026-001");
        booking.setBookingDate(LocalDate.of(2026, 5, 15));
        booking.setPaymentDate(LocalDate.of(2026, 5, 16));
        booking.setStatus("Оплачено");
        booking.setTotalAmount(new BigDecimal("150000.00"));

        assertEquals(101, booking.getCode());
        assertEquals("BR-2026-001", booking.getContractNumber());
        assertEquals(LocalDate.of(2026, 5, 15), booking.getBookingDate());
        assertEquals(LocalDate.of(2026, 5, 16), booking.getPaymentDate());
        assertEquals("Оплачено", booking.getStatus());
        assertEquals(new BigDecimal("150000.00"), booking.getTotalAmount());
    }

    @Test
    void testBookingCreation() {
        assertNotNull(new Booking());
    }
}