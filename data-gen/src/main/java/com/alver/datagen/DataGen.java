package com.alver.datagen;

import com.alver.core.util.EntryImpl;
import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGen {
	
	private static final Logger log = LoggerFactory.getLogger(DataGen.class);
	
	public static void main(String... args) throws SQLException, IOException {
		Map<String, String> arguments = Arrays.stream(args)
			.map(arg -> arg.split("=", 2))
			.map(split -> EntryImpl.of(split[0].replace("--", ""), split[1]))
			.collect(Collectors.toMap(EntryImpl::key, EntryImpl::value));
		
		String url = arguments.getOrDefault("url", "jdbc:hsqldb:hsql://localhost:9000/back_stack");
		String user = arguments.getOrDefault("user", "sa");
		String pass = arguments.getOrDefault("pass", "");
		DataSource dataSource = buildDataSource(url, user, pass);
		Connection conn = dataSource.getConnection();
		DatabaseMetaData meta = conn.getMetaData();
		
		
		String sourceRoot = arguments.getOrDefault("source", "./data-gen/src/main/java");
		String javaPackage = arguments.getOrDefault("package", "com.alver.datagen.generated");
		
		
		ResultSet tableResults = meta.getTables(null, null, "%", new String[]{"TABLE"});
		List<Table> tables = new ArrayList<>(tableResults.getFetchSize());
		while (tableResults.next()) {
			String tableName = tableResults.getString("TABLE_NAME");
			ResultSet columns = meta.getColumns(null, null, tableName, "%");
			
			TableImpl.Builder table = TableImpl.builder().name(tableName);
			while (columns.next()) {
				table.addColumns(ColumnImpl.builder()
					.name(columns.getString("COLUMN_NAME"))
					.type(JDBCType.valueOf(columns.getInt("DATA_TYPE")))
					.nullable(columns.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls)
					.build());
			}
			tables.add(table.build());
		}
		
		Schema schema = SchemaImpl.builder()
			.name("back_stack")
			.addAllTables(tables)
			.build();
		
		log.atInfo().setMessage("SCHEMA").addKeyValue("schema", schema).log();
		
		Path outputDir = Path.of(sourceRoot, javaPackage.split("\\."));
		cleanDirectory(outputDir);
		schema.tables().forEach(table -> {
			String className = Converter.toClassName(table.name());
			String content = Converter.generateClass(javaPackage, List.of(), List.of(), TypeKind.INTERFACE, className, table);
			try {
				Path path = outputDir.resolve(className + ".java");
				Files.writeString(path, content);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	private static void cleanDirectory(Path outputDir) throws IOException {
		Files.createDirectories(outputDir);
		try (Stream<Path> stream = Files.walk(outputDir)) {
			stream.sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
			Files.createDirectories(outputDir);
		}
	}
	
	private static DataSource buildDataSource(String url, String user, String pass) {
		JDBCDataSource dataSource = new JDBCDataSource();
		dataSource.setUrl(url);
		dataSource.setUser(user);
		dataSource.setPassword(pass);
		return dataSource;
	}
	
	
}
