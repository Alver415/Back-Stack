package com.alver.http;

import com.alver.core.util.Immutable;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.immutables.value.Value.Derived;
import tools.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Immutable
@JsonSerialize(as = HttpRequestImpl.class)
public interface HttpRequest {
	
	URI uri();
	
	Headers headers();
	
	HttpMethod method();
	
	String body();
	
	@Derived
	default List<String> pathParams() {
		return List.of(uri().getPath().substring(1).split("/"));
	}
	
	@Derived
	default Map<String, String> queryParams() {
		Map<String, String> params = new HashMap<>();
		String query = uri().getQuery();
		if (query != null) {
			for (String pair : query.split("&")) {
				String[] parts = pair.split("=", 2);
				params.put(parts[0], parts.length > 1 ? parts[1] : "");
			}
		}
		return params;
	}
	
	static HttpRequest from(HttpExchange exchange) throws IOException {
		return HttpRequestImpl.builder()
			.uri(exchange.getRequestURI())
			.headers(exchange.getRequestHeaders())
			.method(HttpMethod.valueOf(exchange.getRequestMethod()))
			.body(new String(exchange.getRequestBody().readAllBytes()))
			.build();
	}
}
