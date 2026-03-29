package com.alver.exception;

import com.alver.exception.NameImpl.BuildFinal;
import com.alver.exception.NameImpl.BuildStart;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static com.alver.exception.ImmutableFunctionTest.Name.full;
import static com.alver.exception.NameImpl.builder;
import static org.immutables.value.Value.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This doesn't test anything in src/main/java. It demonstrates a pattern that can be reused to mimic
 * named functions using @Immutables builders with any number of @Parameters and one @Derived field.
 * Unable to find a way to generalize this to n-arity.
 */
public class ImmutableFunctionTest {
	
	@Immutable
	@Style(
		visibility = Style.ImplementationVisibility.PUBLIC,
		typeImmutable = "*Impl",
		stagedBuilder = true
	)
	public sealed interface Name permits NameImpl {
		
		@Parameter
		String first();
		
		Optional<String> middle();
		
		@Parameter
		String last();
		
		@Derived
		default String full() {
			String middleInitial = middle().map(m -> m.charAt(0)).map("%s. "::formatted).orElse("");
			return "%s %s%s".formatted(first(), middleInitial, last());
		}
		
		// region Static Definitions
		Function<Name, String> full = Name::full;
		
		static String full(Function<BuildStart, BuildFinal> builderFunction) {
			return builderFunction.apply(builder()).build().full();
		}
		
		static String full(BuildFinal builder) {
			return builder.build().full();
		}
		
		static String full(String first, String last) {
			return builder()
				.first(first)
				.last(last)
				.build()
				.full();
		}
		
		static String full(String first, String middle, String last) {
			return builder()
				.first(first)
				.last(last)
				.middle(middle)
				.build()
				.full();
		}
		// endregion Static Definitions
	}
	
	@Test
	void test() {
		assertAll(
			// region Positional Args
			() -> assertEquals("Alexander L. Alvarez",
				full("Alexander", "Luis", "Alvarez")),
			//endregion Positional Args
			
			// region Named Args
			() -> assertEquals("Alexander L. Alvarez",
				builder()
					.first("Alexander")
					.last("Alvarez")
					.middle("Luis")
					.build()
					.full()),
			
			() -> assertEquals("Alexander L. Alvarez",
				full(build -> build
					.first("Alexander")
					.last("Alvarez")
					.middle("Luis")
				)),
			
			() -> assertEquals("Alexander L. Alvarez",
				full(builder()
					.first("Alexander")
					.last("Alvarez")
					.middle("Luis"))),
			
			() -> assertEquals("Alexander Alvarez",
				full(builder()
					.first("Alexander")
					.last("Alvarez")))
			// endregion Named Args
		
		);
		
	}
}
