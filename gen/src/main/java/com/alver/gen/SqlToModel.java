package com.alver.gen;

import com.alver.core.util.Immutable;
import com.alver.functional.exception.TrySupplier;
import com.alver.gen.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.alver.functional.exception.TryFunction.uncheck;
import static com.alver.functional.exception.TrySupplier.uncheck;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Immutable
public interface SqlToModel extends TrySupplier<List<Catalog>> {
	
	Logger log = LoggerFactory.getLogger(SqlToModel.class);
	
	DatabaseMetaData metaData();
	
	Optional<String> catalog();
	
	Optional<String> schemaPattern();
	
	Optional<String> tablePattern();
	
	Optional<String[]> types();
	
	
	@Override
	default List<Catalog> tryGet() throws SQLException {
		ResultSet resultSet = metaData().getTables(
			catalog().orElse(null),
			schemaPattern().orElse(null),
			tablePattern().orElse(null),
			types().orElse(null)
		);
		
		try (resultSet) {
			return stream(resultSet)
				.map(uncheck(TableRow::from))
				.collect(collectingAndThen(groupingBy(
						TableRow::catalog,
						groupingBy(
							TableRow::schema,
							mapping(this::buildTable, toList()))),
					this::toCatalogs));
		}
	}
	
	private Stream<ResultSet> stream(ResultSet resultSet) {
		boolean parallel = false;
		int ordered = Spliterator.ORDERED;
		Spliterator<ResultSet> spliterator = Spliterators.spliteratorUnknownSize(new Iterator<>() {
			@Override
			public boolean hasNext() {
				return uncheck(resultSet::next).get();
			}
			
			@Override
			public ResultSet next() {
				return resultSet;
			}
		}, ordered);
		return StreamSupport.stream(spliterator, parallel);
	}
	
	record TableRow(String catalog, String schema, String table) {
		public TableRow {
			catalog = ofNullable(catalog).orElse("");
			schema = ofNullable(schema).orElse("");
			table = Objects.requireNonNull(table);
		}
		
		public static TableRow from(ResultSet resultSet) throws SQLException {
			return new TableRow(
				resultSet.getString("TABLE_CAT"),
				resultSet.getString("TABLE_SCHEM"),
				resultSet.getString("TABLE_NAME")
			);
		}
	}
	
	private List<Catalog> toCatalogs(Map<String, Map<String, List<Table>>> grouped) {
		return grouped.entrySet().stream()
			.map(catEntry -> CatalogImpl.builder()
				.name(catEntry.getKey())
				.schemas(catEntry.getValue().entrySet().stream()
					.map(schemaEntry -> SchemaImpl.builder()
						.name(schemaEntry.getKey())
						.addAllTables(schemaEntry.getValue())
						.build()
					).toList()
				).build()
			).map(Catalog.class::cast)
			.peek(catalog -> log.atInfo()
				.setMessage("Built Catalog: %s".formatted(catalog.name()))
				.addKeyValue("catalog", catalog)
				.log())
			.toList();
	}
	
	private Table buildTable(TableRow row) {
		try (ResultSet columns = metaData().getColumns(
			row.catalog(),
			row.schema(),
			row.table(),
			"%"
		)) {
			
			Table table = TableImpl.builder()
				.schema(row.schema())
				.name(row.table())
				.addAllColumns(stream(columns)
					.map(this::buildColumn)
					.toList())
				.build();
			
			log.atInfo()
				.setMessage("Built Table: %s, [%s]".formatted(table.name(), table.columns().stream()
					.map(column -> "%s %s".formatted(column.name(), column.type())).collect(joining(", "))))
				.addKeyValue("table", table)
				.log();
			
			return table;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Column buildColumn(ResultSet resultSet) {
		try {
			return ColumnImpl.builder()
				.name(resultSet.getString("COLUMN_NAME"))
				.type(JDBCType.valueOf(resultSet.getInt("DATA_TYPE")))
				.nullable(resultSet.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls)
				.build();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
