package com.alver.app;

import com.alver.api.CrudApi;
import com.alver.api.DatabaseApi;
import com.alver.http.*;
import com.sun.net.httpserver.Headers;
import tools.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

public class ApiRouter implements HttpRouter {
	
	private final ObjectMapper objectMapper;
	
	private final DatabaseApi databaseApi;
	private final Map<String, CrudApi<?>> crudApiMap;
	
	@Inject
	public ApiRouter(
		ObjectMapper objectMapper,
		DatabaseApi databaseApi,
		Map<String, CrudApi<?>> crudApiMap
	) {
		this.objectMapper = objectMapper;
		this.databaseApi = databaseApi;
		this.crudApiMap = crudApiMap;
	}
	
	@Override
	public HttpProcessor route(HttpRequest request) {
		URI uri = request.uri();
		if (uri.getPath().startsWith("/api/sql")) {
			return _ -> {
				Object result = switch (request.method()) {
					case GET -> databaseApi.query(request.body());
					case POST -> {
						databaseApi.execute(request.body());
						yield null;
					}
					default -> throw new UnsupportedOperationException(
						String.format("Unsupported method: %s", request.method()));
				};
				return HttpResponse.of(
					200,
					Headers.of("content-type", "application/json"),
					objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(result)
				);
			};
		}
		CrudApi<?> crudApi = crudApiMap.get(request.pathParams().get(1));
		return _ -> handle(request, crudApi);
	}
	
	private <T> HttpResponse handle(HttpRequest request, CrudApi<T> crudApi) {
		Optional<Long> id = Optional.ofNullable(request.queryParams().get("id")).map(Long::parseLong);
		Object result = switch (request.method()) {
			case GET -> {
				if (id.isPresent()) {
					yield crudApi.getOne(id.get());
				} else {
					yield crudApi.getMany();
				}
			}
			case POST -> crudApi.create(objectMapper.readValue(request.body(), crudApi.createRequestType()));
			case PATCH, PUT ->
				crudApi.update(id.orElseThrow(), objectMapper.readValue(request.body(), crudApi.updateRequestType()));
			case DELETE -> {
				crudApi.delete(id.orElseThrow());
				yield null;
			}
			default -> throw new UnsupportedOperationException(String.format("Unsupported method: %s", request.method()));
		};
		String json = objectMapper.writerWithDefaultPrettyPrinter()
			.writeValueAsString(result);
		return HttpResponse.of(
			200,
			Headers.of("content-type", "application/json"),
			json
		);
	}
}
