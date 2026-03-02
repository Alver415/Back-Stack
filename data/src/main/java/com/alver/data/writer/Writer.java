package com.alver.data.writer;

import com.alver.core.model.Entity;

public interface Writer<T extends Entity> {

    T save(T entity);

    T insert(T entity);

    T update(Long id, T entity);

    void delete(Long id);

}
