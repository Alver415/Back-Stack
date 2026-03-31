package com.alver.core.util;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

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
	optionalAcceptNullable = true,
	overshadowImplementation = true
)
public @interface Immutable {

}
