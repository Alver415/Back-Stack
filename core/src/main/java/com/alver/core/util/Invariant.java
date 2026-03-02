package com.alver.core.util;

public interface Invariant {

    static void check(boolean condition, String message, Object... messageParams) {
        if (!condition) {
            throw new IllegalStateException(String.format(message, messageParams));
        }
    }

    static void nonNull(Object object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
    }
}
