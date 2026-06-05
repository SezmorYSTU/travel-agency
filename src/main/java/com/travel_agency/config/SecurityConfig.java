package com.travel_agency.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Разрешаем статику и страницы входа
                .requestMatchers("/", "/index.html", "/login.html", "/css/**", "/js/**", "/images/**").permitAll()

                // Страницы для ролей
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/manager/**").hasRole("MANAGER")
                .requestMatchers("/operator/**").hasRole("OPERATOR")

                // 1. Оператор: ТОЛЬКО ЧТЕНИЕ (GET) на Туры, Бронирования
                .requestMatchers(HttpMethod.GET, "/api/tours/**", "/api/bookings/**")
                .hasAnyRole("ADMIN", "MANAGER", "OPERATOR")

                // 2. Менеджер+Админ: GET, PUT, POST на Клиентов, Бронирования, Туры
                .requestMatchers(HttpMethod.GET,"/api/clients/**", "/api/bookings/**", "/api/tours/**")
                .hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT,"/api/clients/**", "/api/bookings/**", "/api/tours/**")
                .hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST,"/api/clients/**", "/api/bookings/**", "/api/tours/**")
                .hasAnyRole("ADMIN", "MANAGER")

                // 3. Менеджер+Админ: только ЧТЕНИЕ справочников (Направления, Туроператоры)
                .requestMatchers(HttpMethod.GET, "/api/directions/**", "/api/tour-operators/**")
                .hasAnyRole("ADMIN", "MANAGER")

                // 4. ТОЛЬКО Админ: полное управление справочниками (Направления, Туроператоры)
                // Это правило ДОЛЖНО идти ПОСЛЕ правила 3, чтобы переопределить доступ на изменение
                .requestMatchers("/api/directions/**", "/api/tour-operators/**")
                .hasRole("ADMIN")

                // 5. Менеджер+Админ: чтение + создание Адресов, Паспортов, Предпочтений
                .requestMatchers(HttpMethod.GET, "/api/addresses/**", "/api/passports/**", "/api/client-preferences/**")
                .hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST, "/api/addresses/**", "/api/passports/**", "/api/client-preferences/**")
                .hasAnyRole("ADMIN", "MANAGER")

                // 6. ТОЛЬКО Админ: полное управление Адресами, Паспортами, Предпочтениями (PUT/DELETE)
                .requestMatchers("/api/addresses/**", "/api/passports/**", "/api/client-preferences/**")
                .hasRole("ADMIN")

                // 7. ТОЛЬКО Админ: полное управление персоналом и должностями,
                // но менеждер получает доступ к чтению персонала, но на уровне фронтенда будет видеть только себя
                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/api/employees/**", "/api/job-titles/**")
                .hasRole("ADMIN")

                // 8. Админ: полный CRUD на Клиентов, Бронирования, Туры
                .requestMatchers("/api/clients/**", "/api/bookings/**", "/api/tours/**")
                .hasAnyRole("ADMIN")

                // Всё остальное требует аутентификации
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/dashboard.html", true)
                    .failureUrl("/login.html?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login.html?logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
            )
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        // Для API-запросов возвращаем 403, для страниц — редирект
                        if (request.getRequestURI().startsWith("/api/")) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        } else {
                            response.sendRedirect("/403.html");
                        }
                    })
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}