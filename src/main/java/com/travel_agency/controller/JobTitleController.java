package com.travel_agency.controller;

import com.travel_agency.entity.JobTitle;
import com.travel_agency.repository.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-titles")
@RequiredArgsConstructor
public class JobTitleController {
    private final JobTitleRepository repository;

    @GetMapping
    public List<JobTitle> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<JobTitle> getById(@PathVariable Integer id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JobTitle> create(@RequestBody JobTitle jobTitle) {
        return ResponseEntity.status(201).body(repository.save(jobTitle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTitle> update(@PathVariable Integer id, @RequestBody JobTitle updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setSalary(updated.getSalary());
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