package com.alver.exception;

import com.alver.exception.NamedFunctionTest.NameService.FullNameParameters;
import com.alver.functional.named.NamedArg;
import com.alver.functional.named.NamedFunction;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.alver.exception.NamedFunctionTest.NameService.FullNameParameters.first;
import static com.alver.exception.NamedFunctionTest.NameService.FullNameParameters.last;
import static com.alver.functional.named.NamedArg.arg;
import static com.alver.functional.named.NamedFunction.of;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NamedFunctionTest {
	
	/**
	 * NameService class using NamedFunction with NamedArgs
	 */
	static class NameService {
		
		public record FullNameParameters(String first, String last) {
			public FullNameParameters {
				first = ofNullable(first).orElse("FIRST");
				last = ofNullable(last).orElse("LAST");
			}
			
			public static FullNameParameters of(String first, String last) {
				return new FullNameParameters(first, last);
			}
			
			// region NamedArgs
			public static NamedArg<String> first(String value) {
				return arg("first", value);
			}
			
			public static NamedArg<String> last(String value) {
				return arg("last", value);
			}
			// endregion NamedArgs
		}
		
		private final NamedFunction<FullNameParameters, String> fullName = of(FullNameParameters.class,
			parameters -> "%s %s".formatted(parameters.first(), parameters.last())
		);
		
		public String fullName(Object... args) {
			return fullName.apply(args);
		}
	}
	
	private final String first = "Alan";
	private final String middle = "Benjamin";
	private final String last = "Clark";
	private final NameService nameService = new NameService();
	
	@Test
	void testEmptyArg() {
		// Missing args fallback to defaults defined in record.
		assertEquals("FIRST LAST", nameService.fullName());
	}
	
	@Test
	void testRecordArg() {
		assertEquals("Alan Clark", nameService.fullName(FullNameParameters.of(first, last)));
	}
	
	@Test
	void testMapParameter() {
		assertEquals("Alan Clark", nameService.fullName(Map.of("first", first, "last", last)));
	}
	
	@Test
	void testNamedArgArrayParameter() {
		assertAll(
			// Order doesn't matter when using NamedArg.
			() -> assertEquals("Alan Clark", nameService.fullName(first(first), last(last))),
			() -> assertEquals("Alan Clark", nameService.fullName(last(last), first(first))),
			
			// Extra param doesn't matter when using NamedArg.
			() -> assertEquals("Alan Clark", nameService.fullName(first(first), arg("middle", middle), last(last))),
			() -> assertEquals("Alan Clark", nameService.fullName(first(first), middle, last(last))),
			
			// Missing param does matter when using NamedArg.
//			() -> assertEquals("Alan Clark", nameService.fullName(last(last), middle)),
			
			() -> assertEquals("Alan Clark", nameService.fullName(arg("first", first), arg("last", last))),
			() -> assertEquals("Alan Clark", nameService.fullName(arg("last", last), arg("first", first)))
		);
	}
	
	@Test
	void testObjectArrayParameter() {
		assertEquals("Alan Clark", nameService.fullName(first, last));
	}
	
	@Test
	void testObjectArrayParameterWithWrongOrder() {
		// Unable to determine order of Objects params.
		assertEquals("Clark Alan", nameService.fullName(last, first));
	}
	
}