package com.travel_agency.controller;

import com.travel_agency.entity.Client;
import com.travel_agency.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientRepository repository;

    @GetMapping
    public List<Client> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.status(201).body(repository.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFullName(updated.getFullName());
                    existing.setBirthDate(updated.getBirthDate());
                    existing.setPassport(updated.getPassport());
                    existing.setPhoneNumber(updated.getPhoneNumber());
                    existing.setEmail(updated.getEmail());
                    existing.setAddress(updated.getAddress());
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
