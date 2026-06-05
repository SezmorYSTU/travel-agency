package com.travel_agency.config;

import com.travel_agency.entity.Employee;
import com.travel_agency.entity.TourOperator;
import com.travel_agency.repository.EmployeeRepository;
import com.travel_agency.repository.TourOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepo;
    private final TourOperatorRepository operatorRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Попытка входа для логина: {}", login);

        // 1. Сначала пытаемся найти как СОТРУДНИКА (по логину) — ВАША СТАРАЯ ЛОГИКА
        if (employeeRepo.findByLogin(login).isPresent()) {
            Employee employee = employeeRepo.findByLogin(login).get();

            log.debug("Сотрудник найден: {}, должность={}",
                    employee.getFullName(),
                    employee.getJobTitle() != null ? employee.getJobTitle().getName() : "NULL");

            // Проверка на null перед обращением к полям
            if (employee.getJobTitle() == null) {
                log.error("JobTitle is NULL для сотрудника {}", employee.getLogin());
                throw new RuntimeException("Должность сотрудника не загружена");
            }

            String role = mapJobTitleToRole(employee.getJobTitle().getName());

            // Для операторов находим их компанию по email (у сотрудника и туроператора email совпадает)
            Integer operatorCode = null;
            if (role.equals("ROLE_OPERATOR")) {
                operatorCode = operatorRepo.findByEmail(employee.getEmail())
                        .map(TourOperator::getCode)
                        .orElseThrow(() -> new RuntimeException("Не найден привязанный туроператор для: " + login));
            }

            // Сохраняем код оператора в аттрибутах UserDetails (чтобы использовать в контроллере)
            var attributes = new java.util.HashMap<String, Object>();
            if (operatorCode != null) attributes.put("operatorCode", operatorCode);

            return new User(
                    employee.getLogin(),
                    employee.getPassword(), // Пароль из БД (для сотрудников)
                    Collections.singletonList(new SimpleGrantedAuthority(role))
            );
        }

        // 2. Если сотрудник не найден, пытаемся найти как ТУРОПЕРАТОРА (по Email) — НОВАЯ ЛОГИКА
        log.debug("Сотрудник не найден, пробуем найти Туроператора по Email: {}", login);

        if (operatorRepo.findByEmail(login).isPresent()) {
            TourOperator operator = operatorRepo.findByEmail(login).get();
            log.debug("Туроператор найден: {} (код: {})", operator.getCompanyName(), operator.getCode());

            // Возвращаем роль OPERATOR и пустой пароль (так как используется NoOpPasswordEncoder)
            return new User(
                    operator.getEmail(), // Логин = Email
                    "operator",                  // Пустой пароль для входа без пароля
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERATOR"))
            );
        }

        // 3. Если никто не найден — ошибка
        log.warn("Пользователь не найден ни в Сотрудниках, ни в Туроператорах: {}", login);
        throw new UsernameNotFoundException("Пользователь не найден: " + login);
    }

    private String mapJobTitleToRole(String jobTitleName) {
        if (jobTitleName == null) return "ROLE_OPERATOR";
        String name = jobTitleName.toLowerCase();
        if (name.contains("администратор")) return "ROLE_ADMIN";
        if (name.contains("менеджер")) return "ROLE_MANAGER";
        return "ROLE_OPERATOR";
    }
}