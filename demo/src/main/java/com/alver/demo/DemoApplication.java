package com.alver.demo;

import com.alver.app.AppProperties;
import com.alver.app.DatabaseServer;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	
	public DemoApplication(AppProperties appProperties) {
		
		DemoComponent component = DaggerDemoComponent.factory().create(appProperties);
		
		DatabaseServer databaseServer = component.databaseServer();
		databaseServer.start();
		log.info("DatabaseServer started http://localhost:{}", databaseServer.getPort());
		
		HttpServer httpServer = component.httpServer();
		httpServer.start();
		log.info("HttpServer started http://localhost:{}", httpServer.getAddress().getPort());
	}
}
