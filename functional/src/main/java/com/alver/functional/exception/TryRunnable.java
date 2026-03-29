package com.alver.functional.exception;

import com.alver.functional.result.Result;

import java.util.function.Consumer;

@FunctionalInterface
public interface TryRunnable extends Runnable {
	
	void tryRun() throws Exception;
	
	default Result<Void> safeRun() {
		try {
			tryRun();
			return Result.success(null);
		} catch (Exception e) {
			return Result.failure(e);
		}
	}
	
	@Override
	default void run() {
		try {
			tryRun();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	default void handleException(Exception exception) {
		throw new UncheckedException(exception);
	}
	
	// region Static Utilities
	
	static TryRunnable of(TryRunnable runnable, Consumer<Exception> exceptionHandler) {
		return new TryRunnable() {
			@Override
			public void tryRun() throws Exception {
				runnable.tryRun();
			}
			
			@Override
			public void handleException(Exception exception) {
				exceptionHandler.accept(exception);
			}
		};
	}
	
	static TryRunnable uncheck(TryRunnable runnable) {
		return runnable;
	}
	
	static TrySupplier<Result<Void>> safe(TryRunnable runnable) {
		return runnable::safeRun;
	}
	
	// endregion Static Utilities
	
}