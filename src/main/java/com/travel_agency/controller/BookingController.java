package com.travel_agency.controller;

import com.travel_agency.entity.Booking;
import com.travel_agency.repository.BookingRepository;
import com.travel_agency.repository.TourOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingRepository repository;
    private final TourOperatorRepository operatorRepo;

    @GetMapping
    public List<Booking> getAll(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OPERATOR"))) {
            String currentEmail = auth.getName();

            return operatorRepo.findByEmail(currentEmail)
                    .map(op -> repository.findByTour_Operator_Code(op.getCode()))
                    .orElse(List.of());
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return ResponseEntity.status(201).body(repository.save(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Integer id, @RequestBody Booking updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setClient(updated.getClient());
                    existing.setTour(updated.getTour());
                    existing.setEmployee(updated.getEmployee());
                    existing.setBookingDate(updated.getBookingDate());
                    existing.setPaymentDate(updated.getPaymentDate());
                    existing.setStatus(updated.getStatus());
                    existing.setTotalAmount(updated.getTotalAmount());
                    existing.setContractNumber(updated.getContractNumber());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (repository.existsById(id)) { repository.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }
}