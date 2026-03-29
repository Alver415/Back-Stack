package com.alver.functional.exception;


import java.util.Collection;

public class MultiException extends RuntimeException {
	public MultiException(Collection<Exception> exceptions) {
		super();
		exceptions.forEach(this::addSuppressed);
	}
}
