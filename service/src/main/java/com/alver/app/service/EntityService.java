package com.alver.app.service;

import com.alver.core.model.Entity;
import com.alver.data.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EntityService<T extends Entity> implements IService<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    protected final Repository<T> repository;

    @Autowired
    public EntityService(Repository<T> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        log.debug("getAll");
        return repository.reader().findAll();
    }

    public Optional<T> getById(Long id) {
        log.debug("getById: {}", id);
        return repository.reader().findById(id);
    }

    public T create(T entity) {
        log.debug("create: {}", entity);
        if (entity.id().isPresent()) {
            String message = "Already exists: %s".formatted(entity.id());
            throw new RuntimeException(message);
        }
        return (T) repository.writer().save(entity);
    }

    public T update(Long id, T entity) {
        log.debug("update: {}, {}", id, entity);
        if (!Optional.of(id).equals(entity.id())) {
            String message = "Ids don't match: %s and %s".formatted(id, entity.id());
            throw new RuntimeException(message);
        }
        if (repository.reader().findById(id).isEmpty()) {
            String message = "Not found: %s".formatted(id);
            throw new RuntimeException(message);
        }
        return repository.writer().save(entity);
    }

    public void delete(Long id) {
        log.debug("delete: {}", id);
        if (repository.reader().findById(id).isEmpty()) {
            String message = "Not found: %s".formatted(id);
            throw new RuntimeException(message);
        }
        repository.writer().delete(id);
    }

}
