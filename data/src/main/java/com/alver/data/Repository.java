package com.alver.data;

import com.alver.core.model.Entity;
import com.alver.data.reader.Reader;
import com.alver.data.writer.Writer;

public interface Repository<T extends Entity> {

  Reader<T> reader();

  Writer<T> writer();
}
