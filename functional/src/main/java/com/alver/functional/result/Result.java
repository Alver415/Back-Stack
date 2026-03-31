package com.alver.functional.result;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import java.util.List;

public sealed interface Result<R> permits Failure, Success {
	
	static <R> Success<R> success(R value) {
		return new Success<>(value);
	}
	
	static <R> Failure<R> failure(Exception exception) {
		return new Failure<>(exception);
	}
	
	
	@Immutable
	interface Split<R> {
		
		List<Result<R>> results();
		
		@Derived
		default List<Success<R>> successes() {
			return results().stream()
				.filter(Success.class::isInstance)
				.map(result -> (Success<R>) result)
				.toList();
		}
		
		@Derived
		default List<Failure<R>> failures() {
			return results().stream()
				.filter(Failure.class::isInstance)
				.map(result -> (Failure<R>) result)
				.toList();
		}
	}
}
