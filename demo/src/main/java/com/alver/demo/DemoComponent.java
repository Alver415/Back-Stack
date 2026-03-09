package com.alver.demo;

import com.alver.app.AppModule;
import com.alver.app.AppProperties;
import com.alver.app.DatabaseServer;
import com.sun.net.httpserver.HttpServer;
import dagger.BindsInstance;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
	AppModule.class,
	UserModule.class,
	AddressModule.class
})
public interface DemoComponent {
	
	@Component.Factory
	interface Factory {
		DemoComponent create(@BindsInstance AppProperties appProperties);
	}
	
	DatabaseServer databaseServer();
	
	HttpServer httpServer();
	
}
