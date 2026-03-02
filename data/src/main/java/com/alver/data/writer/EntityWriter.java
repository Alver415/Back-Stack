package com.alver.data.writer;

import com.alver.core.model.Entity;
import com.alver.data.DatabaseClient;

public class EntityWriter<T extends Entity> implements Writer<T> {

    protected final DatabaseClient databaseClient;

    public EntityWriter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public T save(T entity) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public T insert(T entity) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public T update(Long id, T entity) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}