package com.alver.functional.exception;

import com.alver.functional.result.Failure;
import com.alver.functional.result.Result;
import com.alver.functional.result.Success;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface TryFunction<T, R> extends Function<T, R> {
	
	/**
	 * This is the actual logic that can be defined via lambda notation.
	 * It throws Exception to allow lambda definitions which throw Exceptions.
	 *
	 * @param arg the argument of the function.
	 * @return the resulting value of the function.
	 * @throws Exception when the function call fails for any reason.
	 */
	R tryApply(T arg) throws Exception;
	
	/**
	 * This generally delegates to the tryApply function, but returns a Result with either the value or an exception.
	 *
	 * @param arg the argument of the function.
	 * @return the Result, either Success or Failure, containing either the return value or an exception, respectively.
	 */
	default Result<R> safeApply(T arg) {
		try {
			return new Success<>(tryApply(arg));
		} catch (Exception e) {
			return new Failure<>(e);
		}
	}
	
	/**
	 * This generally delegates to the tryApply function, but allows for custom exception handling via overriding handleException.
	 *
	 * @param arg the argument of the function.
	 * @return the resulting value of the function.
	 */
	@Override
	default R apply(T arg) {
		try {
			return tryApply(arg);
		} catch (Exception e) {
			return handleException(arg, e);
		}
	}
	
	/**
	 * This generally delegates to the tryApply function, but allows for custom exception handling via overriding handleException.
	 *
	 * @param exception the Exception from a failed function call.
	 * @return a value
	 * @throws RuntimeException if unable to handle the exception.
	 */
	default R handleException(T arg, Exception exception) {
		throw new UncheckedException(exception);
	}
	
	// region Static Utilities
	
	static <T, R> TryFunction<T, R> of(TryFunction<T, R> function, BiFunction<T, Exception, R> exceptionHandler) {
		return new TryFunction<>() {
			@Override
			public R tryApply(T arg) throws Exception {
				return function.tryApply(arg);
			}
			
			@Override
			public R handleException(T arg, Exception exception) {
				return exceptionHandler.apply(arg, exception);
			}
		};
	}
	
	static <T, R> TryFunction<T, R> uncheck(TryFunction<T, R> function) {
		return function;
	}
	
	static <T, R> TryFunction<T, Result<R>> safe(TryFunction<T, R> function) {
		return function::safeApply;
	}
	
	static <T, R> List<R> multi(List<T> collection, TryFunction<T, R> function) {
		List<Result<R>> results = collection.stream()
			.map(function::safeApply)
			.toList();
		
		List<Exception> failures = results.stream()
			.filter(r -> r instanceof Failure)
			.map(r -> ((Failure<R>) r).exception())
			.toList();
		
		if (!failures.isEmpty()) {
			if (failures.size() == 1) {
				throw new UncheckedException(failures.getFirst());
			}
			throw new MultiException(failures);
		}
		
		
		return results.stream()
			.map(r -> ((Success<R>) r).value())
			.toList();
	}
	
	// endregion Static Utilities
}