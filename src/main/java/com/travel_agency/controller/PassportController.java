package com.travel_agency.controller;

import com.travel_agency.entity.Passport;
import com.travel_agency.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passports")
@RequiredArgsConstructor
public class PassportController {
    private final PassportRepository repository;

    @GetMapping
    public List<Passport> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Passport> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passport> create(@RequestBody Passport passport) {
        return ResponseEntity.status(201).body(repository.save(passport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passport> update(@PathVariable Integer id, @RequestBody Passport updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setSeries(updated.getSeries());
                    existing.setNumber(updated.getNumber());
                    existing.setIssueDate(updated.getIssueDate());
                    existing.setIssuedBy(updated.getIssuedBy());
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
