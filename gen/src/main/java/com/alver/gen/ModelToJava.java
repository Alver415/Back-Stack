package com.alver.gen;

import com.alver.core.util.Immutable;
import com.alver.gen.java.JavaClass;
import com.alver.gen.java.JavaClassImpl;
import com.alver.gen.java.TypeKind;
import com.alver.gen.sql.Catalog;
import org.immutables.value.Value.Derived;

import java.util.List;

import static com.alver.gen.converter.Converters.toClassName;
import static com.alver.gen.converter.Converters.toJavaPackage;
import static java.util.function.Function.identity;

@Immutable
public interface ModelToJava {
	
	String packageRoot();
	
	List<Catalog> catalog();
	
	@Derived
	default List<JavaClass> toJavaClasses() {
		return catalog().stream()
			.flatMap(catalog -> catalog.schemas().stream()
				.map(schema -> schema.tables().stream()
					.map(table -> JavaClassImpl.builder()
						.javaPackage(toJavaPackage("%s.%s".formatted(packageRoot(), table.schema())))
						.imports(List.of())
						.annotations(List.of())
						.kind(TypeKind.INTERFACE)
						.name(toClassName(table.name()))
						.table(table)
						.build())))
			.flatMap(identity())
			.toList();
	}
}
