package com.alver.gen.converter;

public interface Converter<A, B> {
	
	Class<A> fromType();
	
	Class<B> toType();
	
	B from(A from);
	
	A to(B to);
}
