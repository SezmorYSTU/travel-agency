package com.travel_agency.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TourTest {

    @Test
    void testTourGettersAndSetters() {
        Tour tour = new Tour();
        tour.setCode(1);
        tour.setName("Тур в Анталью");
        tour.setCountry("Турция");
        tour.setCity("Анталья");
        tour.setHotel("Rixos Premium");
        tour.setStartDate(LocalDate.of(2026, 6, 1));
        tour.setEndDate(LocalDate.of(2026, 6, 10));
        tour.setPrice(new BigDecimal("150000.00"));
        tour.setAvailableSeats(20);
        tour.setMealType("Все включено");
        tour.setDescription("Пляжный отдых");

        assertEquals(1, tour.getCode());
        assertEquals("Тур в Анталью", tour.getName());
        assertEquals("Турция", tour.getCountry());
        assertEquals("Анталья", tour.getCity());
        assertEquals("Rixos Premium", tour.getHotel());
        assertEquals(LocalDate.of(2026, 6, 1), tour.getStartDate());
        assertEquals(LocalDate.of(2026, 6, 10), tour.getEndDate());
        assertEquals(new BigDecimal("150000.00"), tour.getPrice());
        assertEquals(20, tour.getAvailableSeats());
        assertEquals("Все включено", tour.getMealType());
        assertEquals("Пляжный отдых", tour.getDescription());
    }

    @Test
    void testTourCreation() {
        assertNotNull(new Tour());
    }
}