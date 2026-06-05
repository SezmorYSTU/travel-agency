package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Паспорт\"")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_паспорта\"")
    private Integer code;

    @Column(name = "\"Серия_паспорта\"", nullable = false, length = 4)
    private String series;

    @Column(name = "\"Номер_паспорта\"", nullable = false, length = 6)
    private String number;

    @Column(name = "\"Дата_выдачи\"", nullable = false)
    private LocalDate issueDate;

    @Column(name = "\"Кем_выдан\"", nullable = false, length = 200)
    private String issuedBy;
}