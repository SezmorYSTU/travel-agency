package com.travel_agency.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void testEmployeeGettersAndSetters() {
        Employee emp = new Employee(); // ← Используем стандартный пустой конструктор
        emp.setCode(1);
        emp.setFullName("Иванов И.И.");
        emp.setLogin("ivanov");
        emp.setPassword("pass123");
        emp.setEmail("ivanov@test.ru");
        emp.setPhone("+79991234567");
        emp.setHireDate(LocalDate.of(2024, 1, 15));

        assertEquals(1, emp.getCode());
        assertEquals("Иванов И.И.", emp.getFullName());
        assertEquals("ivanov", emp.getLogin());
        assertEquals("pass123", emp.getPassword());
        assertEquals("ivanov@test.ru", emp.getEmail());
        assertEquals("+79991234567", emp.getPhone());
        assertEquals(LocalDate.of(2024, 1, 15), emp.getHireDate());
    }

    @Test
    void testEmployeeCreation() {
        // Просто проверяем, что объект создаётся без ошибок
        assertNotNull(new Employee());
    }
}
