package com.alver.core.util;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FieldInfo {
	int order() default -1;
	String label() default "";
	String tooltip() default "";
}
