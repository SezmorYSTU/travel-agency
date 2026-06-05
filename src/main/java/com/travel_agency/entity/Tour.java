package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Тур\"")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_тура\"")
    private Integer code;

    @Column(name = "\"Название\"", nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_направления\"", nullable = false)
    private Direction direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_оператора\"", nullable = false)
    private TourOperator operator;

    @Column(name = "\"Страна\"", nullable = false, length = 50)
    private String country;

    @Column(name = "\"Город\"", length = 50)
    private String city;

    @Column(name = "\"Отель\"", length = 100)
    private String hotel;

    @Column(name = "\"Дата_начала\"", nullable = false)
    private LocalDate startDate;

    @Column(name = "\"Дата_окончания\"", nullable = false)
    private LocalDate endDate;

    @Column(name = "\"Стоимость\"", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "\"Кол_во_мест\"", nullable = false)
    private Integer availableSeats;

    @Column(name = "\"Тип_питания\"", length = 50)
    private String mealType;

    @Column(name = "\"Описание\"", columnDefinition = "TEXT")
    private String description;
}