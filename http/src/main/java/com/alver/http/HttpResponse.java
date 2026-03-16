package com.alver.http;

import com.alver.core.util.Immutable;
import com.sun.net.httpserver.Headers;
import tools.jackson.databind.annotation.JsonSerialize;

import java.net.HttpURLConnection;
import java.util.Optional;

@Immutable
@JsonSerialize(as = HttpResponseImpl.class)
public interface HttpResponse {
	
	int code();
	
	Headers headers();
	
	Optional<String> body();
	
	static HttpResponse of(int code, Headers headers, String body) {
		return HttpResponseImpl.builder()
			.code(code)
			.headers(headers)
			.body(body)
			.build();
	}
	
	static HttpResponse ok() {
		return of(HttpURLConnection.HTTP_OK, Headers.of(), null);
	}
	
	static HttpResponse error() {
		return of(HttpURLConnection.HTTP_INTERNAL_ERROR, Headers.of(), null);
	}
}
