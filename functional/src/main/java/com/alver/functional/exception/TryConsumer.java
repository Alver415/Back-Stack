package com.alver.functional.exception;

import com.alver.functional.result.Result;
import com.alver.result.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface TryConsumer<T> extends Consumer<T> {
	
	void tryAccept(T t) throws Exception;
	
	default Result<Void> safeAccept(T arg) {
		try {
			tryAccept(arg);
			return Result.success(null);
		} catch (Exception e) {
			return Result.failure(e);
		}
	}
	
	@Override
	default void accept(T arg) {
		try {
			tryAccept(arg);
		} catch (Exception e) {
			handleException(arg, e);
		}
	}
	
	default void handleException(T arg, Exception exception) {
		throw new UncheckedException(exception);
	}
	
	// region Static Utilities
	
	static <T> TryConsumer<T> of(TryConsumer<T> supplier, BiConsumer<T, Exception> exceptionHandler) {
		return new TryConsumer<>() {
			@Override
			public void tryAccept(T arg) throws Exception {
				supplier.tryAccept(arg);
			}
			
			@Override
			public void handleException(T arg, Exception exception) {
				exceptionHandler.accept(arg, exception);
			}
		};
	}
	
	static <T> TryConsumer<T> uncheck(TryConsumer<T> consumer) {
		return consumer;
	}
	
	static <T> TryFunction<T, Result<Void>> safe(TryConsumer<T> consumer) {
		return consumer::safeAccept;
	}
	
	// endregion Static Utilities
}