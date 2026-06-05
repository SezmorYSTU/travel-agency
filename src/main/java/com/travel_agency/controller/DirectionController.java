package com.travel_agency.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.travel_agency.entity.Direction;
import com.travel_agency.repository.DirectionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/directions")
@RequiredArgsConstructor
public class DirectionController {
    private final DirectionRepository repository;

    // READ ALL: GET /api/directions
    @GetMapping
    public List<Direction> getAll() {
        return repository.findAll();
    }

    // READ ONE: GET /api/directions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Direction> getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)          // Если найдено → 200 OK + JSON
                .orElse(ResponseEntity.notFound().build()); // Если нет → 404 Not Found
    }

    // CREATE: POST /api/directions
    @PostMapping
    public ResponseEntity<Direction> create(@RequestBody Direction direction) {
        Direction saved = repository.save(direction);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    // UPDATE: PUT /api/directions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Direction> update(
            @PathVariable Integer id,
            @RequestBody Direction updated) {

        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setRestType(updated.getRestType());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: DELETE /api/directions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
