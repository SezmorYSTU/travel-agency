package com.travel_agency.controller;

import com.travel_agency.entity.Tour;
import com.travel_agency.repository.TourOperatorRepository;
import com.travel_agency.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {
    private final TourRepository repository;
    private final TourOperatorRepository operatorRepo;

    @GetMapping
    public List<Tour> getAll(Authentication auth) {
        // Если зашёл оператор -> фильтруем
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_OPERATOR"))) {
            String currentEmail = auth.getName(); // Это Email туроператора

            // Находим объект туроператора и его код
            return operatorRepo.findByEmail(currentEmail)
                    .map(op -> repository.findByOperator_Code(op.getCode()))
                    .orElse(List.of());
        }
        // Админ и Менеджер видят всё
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tour> create(@RequestBody Tour tour) {
        return ResponseEntity.status(201).body(repository.save(tour));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> update(@PathVariable Integer id, @RequestBody Tour updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setDirection(updated.getDirection());
                    existing.setOperator(updated.getOperator());
                    existing.setCountry(updated.getCountry());
                    existing.setCity(updated.getCity());
                    existing.setHotel(updated.getHotel());
                    existing.setStartDate(updated.getStartDate());
                    existing.setEndDate(updated.getEndDate());
                    existing.setPrice(updated.getPrice());
                    existing.setAvailableSeats(updated.getAvailableSeats());
                    existing.setMealType(updated.getMealType());
                    existing.setDescription(updated.getDescription());
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
