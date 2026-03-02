package com.alver.api.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface Controller<T> {

    ResponseEntity<T> create();
    ResponseEntity<T> update(Long id);
    ResponseEntity<Void> delete(Long id);
    ResponseEntity<List<T>> getMany();
    ResponseEntity<Optional<T>> getOne(Long id);
}
