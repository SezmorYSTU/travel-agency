package com.travel_agency.repository;

import com.travel_agency.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findByLogin_WhenExists_ShouldReturnEmployee() {
        Optional<Employee> result = employeeRepository.findByLogin("admin_sm");

        assertTrue(result.isPresent());
        assertEquals("admin_sm", result.get().getLogin());
        assertNotNull(result.get().getFullName());
    }

    @Test
    void findByLogin_WhenNotExists_ShouldReturnEmpty() {
        Optional<Employee> result = employeeRepository.findByLogin("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistEmployee() {
        Employee employee = new Employee();
        employee.setLogin("test_user");
        employee.setPassword("pass123");
        employee.setFullName("Test User");
        employee.setEmail("test@example.ru");
        employee.setPhone("+79990000000");
        employee.setHireDate(LocalDate.now());

        Employee saved = employeeRepository.save(employee);

        assertNotNull(saved.getCode());
        assertEquals("test_user", saved.getLogin());
    }

    @Test
    void findAll_ShouldReturnList() {
        var employees = employeeRepository.findAll();

        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }
}