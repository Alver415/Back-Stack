package com.alver.http;

public interface HttpRequestHandler {
  HttpResponse handle(HttpRequest request);
}
