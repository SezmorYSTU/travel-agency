package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Сотрудник\"")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_сотрудника\"")
    private Integer code;

    @Column(name = "\"ФИО\"", nullable = false, length = 100)
    private String fullName;

    @Column(name = "\"Дата_приема\"", nullable = false)
    private LocalDate hireDate;

    @Column(name = "\"Телефон\"", length = 12)
    private String phone;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "\"Логин\"", nullable = false, unique = true, length = 50)
    private String login;

    @Column(name = "\"Пароль\"", nullable = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_должности\"", nullable = false)
    private JobTitle jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_адреса\"", nullable = false)
    private Address address;
}
