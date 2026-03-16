package com.alver.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface HttpProcessor extends HttpHandler {
	
	Logger log = LoggerFactory.getLogger(HttpProcessor.class);
	
	@Override
	default void handle(HttpExchange exchange) {
		try (exchange) {
			
			HttpRequest request = HttpRequest.from(exchange);
			log.atInfo().setMessage("Received request.").addKeyValue("request", request).log();
			
			HttpResponse response = handle(request);
			log.atInfo().setMessage("Returning response.").addKeyValue("response", response).log();
			
			int code = response.code();
			byte[] body = response.body().map(String::getBytes).orElseGet(() -> new byte[0]);
			
			exchange.sendResponseHeaders(code, body.length);
			exchange.getResponseBody().write(body);
			exchange.getResponseBody().flush();
			
		} catch (Exception e) {
			log.error("Unknown error.", e);
		}
	}
	
	HttpResponse handle(HttpRequest request);
}
