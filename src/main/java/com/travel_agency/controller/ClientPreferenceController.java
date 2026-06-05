package com.travel_agency.controller;

import com.travel_agency.entity.ClientPreference;
import com.travel_agency.repository.ClientPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-preferences")
@RequiredArgsConstructor
public class ClientPreferenceController {
    private final ClientPreferenceRepository repository;

    @GetMapping
    public List<ClientPreference> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ClientPreference> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientPreference> create(@RequestBody ClientPreference preference) {
        return ResponseEntity.status(201).body(repository.save(preference));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientPreference> update(@PathVariable Integer id, @RequestBody ClientPreference updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setClient(updated.getClient());
                    existing.setDirection(updated.getDirection());
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