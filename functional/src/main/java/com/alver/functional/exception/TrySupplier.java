package com.alver.functional.exception;

import com.alver.functional.result.Failure;
import com.alver.functional.result.Result;
import com.alver.functional.result.Success;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface TrySupplier<R> extends Supplier<R> {
	
	R tryGet() throws Exception;
	
	default Result<R> safeGet() {
		try {
			return new Success<>(tryGet());
		} catch (Exception e) {
			return new Failure<>(e);
		}
	}
	
	@Override
	default R get() {
		try {
			return tryGet();
		} catch (Exception e) {
			return handleException(e);
		}
	}
	
	default R handleException(Exception exception) {
		throw new UncheckedException(exception);
	}
	
	
	// region Static Utilities
	static <R> TrySupplier<R> of(TrySupplier<R> supplier, TryFunction<Exception, R> exceptionHandler) {
		return new TrySupplier<>() {
			@Override
			public R tryGet() throws Exception {
				return supplier.tryGet();
			}
			
			@Override
			public R handleException(Exception exception) {
				return exceptionHandler.apply(exception);
			}
		};
	}
	
	static <R> TrySupplier<R> uncheck(TrySupplier<R> supplier) {
		return supplier;
	}
	
	static <R> TrySupplier<Result<R>> safe(TrySupplier<R> supplier) {
		return supplier::safeGet;
	}
	
	@SafeVarargs
	static <R> List<R> multi(TrySupplier<R>... suppliers) {
		List<Result<R>> results = Arrays.stream(suppliers)
			.map(TrySupplier::safeGet)
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