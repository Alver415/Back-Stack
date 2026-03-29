package com.alver.functional.named;

import java.util.Map.Entry;

public interface NamedArg<T> {
	String name();
	
	T value();
	
	static <T> NamedArg<T> from(Entry<String, T> entry) {
		return arg(entry.getKey(), entry.getValue());
	}
	
	static <T> NamedArg<T> arg(String name, T value) {
		return new NamedArg<T>() {
			@Override
			public String name() {
				return name;
			}
			
			@Override
			public T value() {
				return value;
			}
		};
	}
	
}