package com.alver.http;

import com.alver.core.util.Immutable;
import com.sun.net.httpserver.Headers;
import tools.jackson.databind.annotation.JsonSerialize;

@Immutable
@JsonSerialize(as = HttpResponseImpl.class)
public interface HttpResponse {
	
	int code();
	
	Headers headers();
	
	String body();
	
	static HttpResponse of(int code, Headers headers, String body) {
		return HttpResponseImpl.builder().code(code).headers(headers).body(body).build();
	}
}
