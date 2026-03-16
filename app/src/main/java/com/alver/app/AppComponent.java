package com.alver.app;

import com.alver.data.DatabaseClient;
import com.sun.net.httpserver.HttpServer;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
	
	@Component.Factory
	interface Factory {
		AppComponent create(@BindsInstance AppProperties appProperties);
	}
	
	HttpServer httpServer();
	
}
