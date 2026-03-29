package com.alver.functional.exception;

public class UncheckedException extends RuntimeException {
	public UncheckedException(Exception cause) {
		super(cause);
	}
}
