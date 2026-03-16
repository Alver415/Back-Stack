package com.alver.demo;

import com.alver.app.AppProperties;
import com.alver.app.DatabaseServer;
import com.alver.app.PropertiesLoader;
import com.alver.data.DatabaseClient;
import com.alver.data.SqlResourceLoader;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class DemoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	
	public static void main(String... args) throws IOException {
		
		AppProperties appProperties = PropertiesLoader.load(args);
		DemoComponent component = DaggerDemoComponent.factory().create(appProperties);
		
		if (Arrays.asList(args).contains("--run=db-server")) {
			DatabaseServer databaseServer = component.databaseServer();
			databaseServer.start();
			log.info("DatabaseServer started http://localhost:{}", databaseServer.getPort());
			
			// TODO: Cleanup hacky db init.
			DatabaseClient databaseClient = component.databaseClient();
			Arrays.stream(SqlResourceLoader.load("/sql/init.demo.sql").split(";"))
				.filter(s -> !s.isBlank())
				.map("%s;"::formatted)
				.forEachOrdered(databaseClient::execute);
		}
		
		if (Arrays.asList(args).contains("--run=http-server")) {
			HttpServer httpServer = component.httpServer();
			httpServer.start();
			log.info("HttpServer started http://localhost:{}", httpServer.getAddress().getPort());
		}
	}
}
