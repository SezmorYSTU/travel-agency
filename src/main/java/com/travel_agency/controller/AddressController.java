package com.travel_agency.controller;

import com.travel_agency.entity.Address;
import com.travel_agency.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressRepository repository;

    @GetMapping
    public List<Address> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        return ResponseEntity.status(201).body(repository.save(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Integer id, @RequestBody Address updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setRegion(updated.getRegion());
                    existing.setDistrict(updated.getDistrict());
                    existing.setCity(updated.getCity());
                    existing.setStreet(updated.getStreet());
                    existing.setHouse(updated.getHouse());
                    existing.setPremise(updated.getPremise());
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
