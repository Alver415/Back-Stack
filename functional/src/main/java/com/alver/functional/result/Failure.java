package com.alver.functional.result;

public record Failure<R>(Exception exception) implements Result<R> {
}
	