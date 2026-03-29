package com.alver.functional.result;

public sealed interface Result<R> permits Failure, Success {
	
	static <R> Success<R> success(R value) {
		return new Success<>(value);
	}
	
	static <R> Failure<R> failure(Exception exception) {
		return new Failure<>(exception);
	}
	
}
