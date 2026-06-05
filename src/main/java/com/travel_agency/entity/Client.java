package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Клиент\"")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_клиента\"")
    private Integer code;

    @Column(name = "\"ФИО\"", nullable = false, length = 100)
    private String fullName;

    @Column(name = "\"Дата_рождения\"")
    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_паспорта\"", nullable = false)
    private Passport passport;

    @Column(name = "\"Номер_телефона\"", nullable = false, length = 12)
    private String phoneNumber;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_адреса\"", nullable = false)
    private Address address;
}
