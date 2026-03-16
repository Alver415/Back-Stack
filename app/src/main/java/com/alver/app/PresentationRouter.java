package com.alver.app;

import com.alver.http.HttpProcessor;
import com.alver.http.HttpRequest;
import com.alver.http.HttpResponse;
import com.alver.http.HttpRouter;
import com.alver.web.presenter.Presenter;
import com.sun.net.httpserver.Headers;

import javax.inject.Inject;
import java.util.Map;

public class PresentationRouter implements HttpRouter {
	
	private final Map<String, Presenter> presenters;
	
	@Inject
	public PresentationRouter(
		Map<String, Presenter> presenters
	) {
		this.presenters = presenters;
	}
	
	@Override
	public HttpProcessor route(HttpRequest request) {
		String path = request.uri().getPath();
		Presenter presenter = presenters.getOrDefault(path.substring(1), presenters.get("home"));
		
		return _ -> HttpResponse.of(
			200,
			Headers.of("content-type", "text/html"),
			presenter.render()
		);
	}
}
