package com.travel_agency.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AllEntitiesCoverageTest {

    @Test
    void testEmployeeEntity() {
        Employee e = new Employee();
        e.setCode(1);
        e.setFullName("Test");
        e.setLogin("test");
        e.setPassword("pass");
        e.setEmail("test@test.ru");
        e.setPhone("+79990000000");
        e.setHireDate(LocalDate.now());

        assertEquals(1, e.getCode());
        assertNotNull(e.getFullName());
    }

    @Test
    void testTourEntity() {
        Tour t = new Tour();
        t.setCode(1);
        t.setName("Tour");
        t.setCountry("Russia");
        t.setCity("Moscow");
        t.setPrice(new BigDecimal("50000"));
        t.setAvailableSeats(10);
        t.setStartDate(LocalDate.now());
        t.setEndDate(LocalDate.now().plusDays(7));

        assertEquals("Tour", t.getName());
        assertTrue(t.getPrice().compareTo(new BigDecimal("0")) > 0);
    }

    @Test
    void testBookingEntity() {
        Booking b = new Booking();
        b.setCode(1);
        b.setContractNumber("CONTRACT-001");
        b.setBookingDate(LocalDate.now());
        b.setStatus("Confirmed");
        b.setTotalAmount(new BigDecimal("50000"));

        assertEquals("CONTRACT-001", b.getContractNumber());
        assertNotNull(b.getBookingDate());
    }

    @Test
    void testTourOperatorEntity() {
        TourOperator to = new TourOperator();
        to.setCode(1);
        to.setCompanyName("Test Operator");
        to.setEmail("test@operator.ru");
        to.setInn("1234567890");
        to.setRating(new BigDecimal("5.0"));

        assertEquals("Test Operator", to.getCompanyName());
        assertTrue(to.getRating().signum() > 0);
    }
}