package com.alver.gen;

import com.alver.core.util.Immutable;
import com.alver.functional.exception.MultiException;
import com.alver.functional.result.Failure;
import com.alver.gen.java.JavaClass;
import com.alver.gen.sql.Catalog;
import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.alver.functional.exception.TryConsumer.safe;
import static org.immutables.value.Value.Default;

public class Main {
	
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String... cliArgs) throws Exception {
		Args args = Args.fromCli(cliArgs);
		
		List<Catalog> catalogs = SqlToModelImpl.builder()
			.metaData(getDataSource(args).getConnection().getMetaData())
			.catalog(args.catalog())
			.schemaPattern(args.schemaPattern())
			.tablePattern(args.tablePattern())
			.types(args.types())
			.build()
			.tryGet();
		
		List<JavaClass> javaClasses = ModelToJavaImpl.builder()
			.catalog(catalogs)
			.packageRoot(args.packageRoot())
			.build()
			.toJavaClasses();
		
		writeToDirectory(args.sourceRoot(), javaClasses);
	}
	
	private static void writeToDirectory(Path sourceRoot, List<JavaClass> javaClasses) throws MultiException {
		List<Exception> exceptions = javaClasses.stream()
			.map(safe(javaClass -> writeToFile(sourceRoot, javaClass)))
			.filter(Failure.class::isInstance)
			.map(Failure.class::cast)
			.map(Failure::exception)
			.toList();
		if (!exceptions.isEmpty()) {
			throw new MultiException(exceptions);
		}
	}
	
	private static void writeToFile(Path sourceRoute, JavaClass javaClass) throws IOException {
		Path path = sourceRoute
			.resolve(String.join("/", javaClass.javaPackage().split("\\.")))
			.resolve(javaClass.fileName());
		
		log.atDebug().setMessage("writing to file")
			.addKeyValue("path", path)
			.addKeyValue("javaClass", javaClass)
			.log();
		
		Files.createDirectories(path.getParent());
		Files.writeString(path, javaClass.generate());
	}
	
	private static DataSource getDataSource(Args args) {
		JDBCDataSource dataSource = new JDBCDataSource();
		dataSource.setUrl(args.url());
		dataSource.setUser(args.username());
		dataSource.setPassword(args.password().orElse(""));
		return dataSource;
	}
	
	
	@Immutable
	public interface Args {
		
		static Args fromCli(String... args) throws Exception {
			log.atDebug().setMessage("Parsing CLI args").addKeyValue("args", args).log();
			return ApplicationProperties.bind(Args.class, args);
		}
		
		String url();
		
		@Default
		default String username() {
			return "sa";
		}
		
		Optional<String> password();
		
		Optional<String> catalog();
		
		Optional<String> schemaPattern();
		
		Optional<String> tablePattern();
		
		Optional<String[]> types();
		
		
		@Default
		default Path sourceRoot() {
			return Path.of("./target/generated-sources/gen");
		}
		
		@Default
		default String packageRoot() {
			return "com.alver.gen";
		}
	}
}
