package com.alver.gen;

import com.alver.core.util.Immutable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Immutable
public interface ResultSetHelper {
	
	ResultSet resultSet();
	
	default Map<Long, Map<String, Object>> asMap() throws SQLException {
		Map<Long, Map<String, Object>> map = new HashMap<>();
		try (ResultSet resultSet = resultSet()){
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 0; i < columnCount; i++){
				String name = metaData.getColumnName(i);
				String label = metaData.getColumnLabel(i);
				int type = metaData.getColumnType(i);
			}
			while (resultSet.next()){
			
			}
		}
		return map;
	}
}
