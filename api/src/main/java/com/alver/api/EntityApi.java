package com.alver.api;

import com.alver.app.service.Service;
import com.alver.core.model.Entity;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EntityApi<T extends Entity> implements CrudApi<T> {
	
	protected final Service<T> service;
	protected final Class<? extends CreateRequest<T>> createRequestType;
	protected final Class<? extends UpdateRequest<T>> updateRequestType;
	
	@Inject
	public EntityApi(
		Service<T> service,
		Class<? extends CreateRequest<T>> createRequestType,
		Class<? extends UpdateRequest<T>> updateRequestType
	) {
		this.service = service;
		this.createRequestType = createRequestType;
		this.updateRequestType = updateRequestType;
	}
	
	public T create(CreateRequest<T> createRequest) {
		return service.create(null);
	}
	
	public T update(Long id, UpdateRequest<T> update) {
		return service.update(id, null);
	}
	
	public void delete(Long id) {
		service.delete(id);
	}
	
	public List<T> getMany() {
		return service.getAll();
	}
	
	public Optional<T> getOne(Long id) {
		return service.getById(id);
	}
	
	@Override
	public Class<? extends CreateRequest<T>> createRequestType() {
		return createRequestType;
	}
	
	@Override
	public Class<? extends UpdateRequest<T>> updateRequestType() {
		return updateRequestType;
	}
}
