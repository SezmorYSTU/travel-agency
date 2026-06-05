package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Адрес\"")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_адреса\"")
    private Integer code;

    @Column(name = "\"Область\"", nullable = false, length = 100)
    private String region;

    @Column(name = "\"Район\"", length = 100)
    private String district;

    @Column(name = "\"Город\"", nullable = false, length = 100)
    private String city;

    @Column(name = "\"Улица\"", nullable = false, length = 100)
    private String street;

    @Column(name = "\"Дом\"", nullable = false, length = 10)
    private String house;

    @Column(name = "\"Помещение\"", length = 20)
    private String premise;
}