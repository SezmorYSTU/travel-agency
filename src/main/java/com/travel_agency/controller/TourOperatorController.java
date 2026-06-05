package com.travel_agency.controller;

import com.travel_agency.entity.TourOperator;
import com.travel_agency.repository.TourOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-operators")
@RequiredArgsConstructor
public class TourOperatorController {
    private final TourOperatorRepository repository;

    @GetMapping
    public List<TourOperator> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<TourOperator> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TourOperator> create(@RequestBody TourOperator operator) {
        return ResponseEntity.status(201).body(repository.save(operator));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourOperator> update(@PathVariable Integer id, @RequestBody TourOperator updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCompanyName(updated.getCompanyName());
                    existing.setInn(updated.getInn());
                    existing.setAddress(updated.getAddress());
                    existing.setContactPerson(updated.getContactPerson());
                    existing.setPhone(updated.getPhone());
                    existing.setEmail(updated.getEmail());
                    existing.setRating(updated.getRating());
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