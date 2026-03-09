package com.alver.data.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ResultSetMapper<T> {
	
	Logger log = LoggerFactory.getLogger(ResultSetMapper.class);
	
	T map(Map<String, Object> rowData) throws SQLException;
	
	default List<T> mapAll(ResultSet resultSet) throws SQLException {
		List<T> list = new ArrayList<>(resultSet.getFetchSize());
		while (resultSet.next()) {
			list.add(mapOne(resultSet));
		}
		return list;
	}
	
	default T mapOne(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		Map<String, Object> row = new HashMap<>(metaData.getColumnCount());
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String columnName = metaData.getColumnLabel(i);
			Object value = resultSet.getObject(i);
			row.put(columnName, value);
		}
		T mapped = map(row);
		
		log.atDebug().setMessage("Mapped ResultSet:")
			.addKeyValue("input", row)
			.addKeyValue("output", mapped)
			.log();
		
		return mapped;
	}
}
