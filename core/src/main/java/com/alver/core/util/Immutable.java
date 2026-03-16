package com.alver.core.util;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.immutables.value.Value.Style;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@JsonSerialize
@JsonDeserialize
@Style(
	visibility = Style.ImplementationVisibility.PUBLIC,
	typeImmutable = "*Impl",
	passAnnotations = FieldInfo.class,
	allMandatoryParameters = true,
	optionalAcceptNullable = true
)
public @interface Immutable {
}
