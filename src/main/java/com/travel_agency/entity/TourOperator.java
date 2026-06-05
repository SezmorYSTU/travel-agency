package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Туроператор\"")
public class TourOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_оператора\"")
    private Integer code;

    @Column(name = "\"Название_компании\"", nullable = false, unique = true, length = 100)
    private String companyName;

    @Column(name = "\"ИНН\"", nullable = false, unique = true, length = 12)
    private String inn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_адреса\"", nullable = false, unique = true)
    private Address address;

    @Column(name = "\"Контактное_лицо\"", length = 100)
    private String contactPerson;

    @Column(name = "\"Телефон\"", length = 12)
    private String phone;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "\"Рейтинг\"", precision = 3, scale = 2)
    private BigDecimal rating;
}