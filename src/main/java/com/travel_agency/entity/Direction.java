package com.travel_agency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Направление\"")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_направления\"")
    private Integer code;

    @Column(name = "\"Название\"", nullable = false, length = 50)
    private String name;

    @Column(name = "\"Тип_отдыха\"", length = 50)
    private String restType;
}
