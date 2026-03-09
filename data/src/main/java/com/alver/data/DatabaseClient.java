package com.alver.data;

import com.alver.data.mapper.ResultSetMapper;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseClient {
	
	private final DataSource dataSource;
	
	@Inject
	public DatabaseClient(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Map<String, Object>> query(String sql) {
		return query(sql, rowData -> rowData);
	}
	
	public <T> List<T> query(String sql, ResultSetMapper<T> resultSetMapper) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			return resultSetMapper.mapAll(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void execute(String sql) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
