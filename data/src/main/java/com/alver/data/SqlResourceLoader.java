package com.alver.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public interface SqlResourceLoader {

    static String load(String path) {
        try (InputStream is = SqlResourceLoader.class.getResourceAsStream(path)) {
            Objects.requireNonNull(is);
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
