package com.alver.data.reader;

import com.alver.core.model.*;
import com.alver.data.DatabaseClient;
import com.alver.data.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

public class EntityReader<T extends Entity> implements Reader<T> {
	
	protected final DatabaseClient databaseClient;
	protected final EntityMapper<T> mapper;
	protected final String selectSql;
	
	
	@Inject
	public EntityReader(DatabaseClient databaseClient, EntityMapper<T> mapper, String selectSql) {
		this.databaseClient = databaseClient;
		this.mapper = mapper;
		this.selectSql = selectSql;
	}
	
	@Override
	public List<T> findAll() {
		return databaseClient.query(selectSql, mapper);
	}
	
	@Override
	public Optional<T> findById(Long id) {
		return findAll().stream().findFirst();
	}
}
