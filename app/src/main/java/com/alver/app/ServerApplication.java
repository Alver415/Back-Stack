package com.alver.app;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerApplication {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public ServerApplication(AppProperties appProperties) {

        AppComponent component = DaggerAppComponent.factory().create(appProperties);

        DatabaseServer databaseServer = component.databaseServer();
        databaseServer.start();
        log.info("DatabaseServer started http://localhost:{}", databaseServer.getPort());

        HttpServer httpServer = component.httpServer();
        httpServer.start();
        log.info("HttpServer started http://localhost:{}", httpServer.getAddress().getPort());
    }
}
