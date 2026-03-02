package com.alver.data.reader;

import com.alver.core.model.Entity;
import com.alver.data.DatabaseClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EntityReader<T extends Entity> implements Reader<T> {

    protected final DatabaseClient databaseClient;

    @Autowired
    public EntityReader(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Optional<T> findById(Long id) {
        return findAll().stream().findFirst();
    }
}