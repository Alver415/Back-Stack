package com.alver.api.controller;

import com.alver.core.model.Entity;
import com.alver.app.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public abstract class EntityController<T extends Entity> {

    protected final IService<T> service;

    public EntityController(IService<T> service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<T> getById(
            @PathVariable("id") Long id) {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<T> create(
            @RequestBody T entity) {
        return ResponseEntity.ok(service.create(entity));
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<T> update(
            @PathVariable("id") Long id,
            @RequestBody T updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
