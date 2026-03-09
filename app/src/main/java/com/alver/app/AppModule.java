package com.alver.app;

import com.alver.api.CrudApi;
import com.alver.http.HttpExchangeHandler;
import com.alver.web.app.HtmlPresenter;
import com.alver.web.home.HomePresenter;
import com.alver.web.presenter.Presenter;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;
import org.hsqldb.jdbc.JDBCDataSource;
import tools.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Module
public interface AppModule {
	
	@Multibinds
	Map<String, CrudApi<?>> crudApis();
	
	@Binds
	MustacheFactory mustacheFactory(DefaultMustacheFactory factory);
	
	@Provides
	static ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	
	@Provides
	static DefaultMustacheFactory provideDefaultMustacheFactory() {
		DefaultMustacheFactory factory = new DefaultMustacheFactory();
		factory.setRecursionLimit(10);
		return factory;
	}
	
	@Provides
	static HtmlPresenter htmlPresenter(MustacheFactory mustacheFactory, AppProperties properties) {
		return new HtmlPresenter(mustacheFactory, properties.title(), properties.version());
	}
	
	@Provides
	@IntoMap
	@StringKey("home")
	static Presenter homePresenter(HtmlPresenter htmlPresenter, MustacheFactory factory) {
		return new HomePresenter(htmlPresenter, factory);
	}
	
	@Provides
	static DatabaseServer provideDatabaseServer(AppProperties appProperties) {
		DatabaseServer databaseServer = new DatabaseServer();
		databaseServer.setNoSystemExit(true);
		databaseServer.setDatabasePath(0, appProperties.databaseServerPath());
		databaseServer.setDatabaseName(0, appProperties.databaseServerName());
		databaseServer.setAddress(appProperties.databaseServerAddress());
		databaseServer.setPort(appProperties.databaseServerPort());
		return databaseServer;
	}
	
	@Provides
	static DataSource provideDataSource(AppProperties appProperties) {
		JDBCDataSource dataSource = new JDBCDataSource();
		dataSource.setUrl(appProperties.databaseClientUrl());
		dataSource.setUser(appProperties.databaseClientUser());
		dataSource.setPassword(appProperties.databaseClientPass());
		return dataSource;
	}
	
	@Provides
	static HttpServer provideHttpServer(
		AppProperties appProperties,
		PresentationRouter presentationRouter,
		ApiRouter apiRouter
	) {
		try {
			int corePoolSize = 4;
			int maximumPoolSize = 16;
			int keepAliveTime = 1000;
			TimeUnit milliseconds = TimeUnit.MILLISECONDS;
			ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(16);
			ThreadPoolExecutor executor = new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, milliseconds, workQueue);
			InetSocketAddress inetSocketAddress = new InetSocketAddress(appProperties.httpServerPort());
			HttpServer server = HttpServer.create(inetSocketAddress, appProperties.httpServerBacklog());
			server.setExecutor(executor);
			
			HttpExchangeHandler presentationHandler = (request) -> presentationRouter.route(request).handle(request);
			HttpExchangeHandler apiHandler = (request) -> apiRouter.route(request).handle(request);
			
			HttpContext root = server.createContext("/", presentationHandler);
			HttpContext api = server.createContext("/api", apiHandler);
			
			return server;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
}
