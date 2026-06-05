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
@Table(name = "\"Бронирование\"")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Код_бронирования\"")
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_клиента\"", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_тура\"", nullable = false)
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Код_сотрудника\"", nullable = false)
    private Employee employee;

    @Column(name = "\"Дата_бронирования\"", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "\"Дата_оплаты\"")
    private LocalDate paymentDate;

    @Column(name = "\"Статус\"", nullable = false, length = 20)
    private String status;

    @Column(name = "\"Итоговая_сумма\"", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "\"Номер_договора\"", nullable = false, unique = true, length = 20)
    private String contractNumber;
}
