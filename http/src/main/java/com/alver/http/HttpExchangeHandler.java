package com.alver.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface HttpExchangeHandler extends HttpHandler {
	
	Logger log = LoggerFactory.getLogger(HttpExchangeHandler.class);
	
	@Override
	default void handle(HttpExchange exchange) {
		try (exchange) {
			HttpRequest request = HttpRequest.of(exchange);
			log.atInfo().setMessage("Received a request.").addKeyValue("request", request).log();
			HttpResponse response = handle(request);
			log.atInfo().setMessage("Returning a response.").addKeyValue("response", response).log();
			
			int code = response.code();
			byte[] body = response.body().getBytes();
			exchange.sendResponseHeaders(code, body.length);
			exchange.getResponseBody().write(body);
			exchange.getResponseBody().flush();
		} catch (Exception e) {
			log.error("Unknown error.", e);
		}
	}
	
	HttpResponse handle(HttpRequest request);
}
