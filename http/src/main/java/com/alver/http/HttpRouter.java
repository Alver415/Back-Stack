package com.alver.http;

public interface HttpRouter {
  HttpProcessor route(HttpRequest request);
}
