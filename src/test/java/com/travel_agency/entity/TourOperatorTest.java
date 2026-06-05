package com.travel_agency.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TourOperatorTest {

    @Test
    void testTourOperatorGettersAndSetters() {
        TourOperator operator = new TourOperator();
        operator.setCode(5);
        operator.setCompanyName("Pegas Touristik");
        operator.setInn("7701234567");
        operator.setContactPerson("Иванов И.И.");
        operator.setPhone("+74951234567");
        operator.setEmail("info@pegas.ru");
        operator.setRating(new BigDecimal("9.5"));

        assertEquals(5, operator.getCode());
        assertEquals("Pegas Touristik", operator.getCompanyName());
        assertEquals("7701234567", operator.getInn());
        assertEquals("Иванов И.И.", operator.getContactPerson());
        assertEquals("+74951234567", operator.getPhone());
        assertEquals("info@pegas.ru", operator.getEmail());
        assertEquals(new BigDecimal("9.5"), operator.getRating());
    }

    @Test
    void testTourOperatorCreation() {
        assertNotNull(new TourOperator());
    }
}
