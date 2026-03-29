package com.alver.functional.result;

public record Success<R>(R value) implements Result<R> {
}
