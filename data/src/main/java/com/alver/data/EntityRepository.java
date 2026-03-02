package com.alver.data;

import com.alver.core.model.Entity;
import com.alver.data.reader.Reader;
import com.alver.data.writer.Writer;

public class EntityRepository<T extends Entity> implements Repository<T> {

    private final Reader<T> reader;
    private final Writer<T> writer;

    public EntityRepository(
            Reader<T> reader,
            Writer<T> writer
    ) {
        this.reader = reader;
        this.writer = writer;
    }


    @Override
    public Reader<T> reader() {
        return this.reader;
    }

    @Override
    public Writer<T> writer() {
        return this.writer;
    }
}
