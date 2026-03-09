package com.alver.http;

public interface HttpRouter {
  HttpRequestHandler route(HttpRequest request);
}
