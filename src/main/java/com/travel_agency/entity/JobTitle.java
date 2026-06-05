package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Должность\"")
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_должности\"")
    private Integer code;

    @Column(name = "\"Название\"", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "\"Оклад\"", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;
}