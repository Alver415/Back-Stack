package com.alver.app;

import com.alver.core.util.Slf4jWriter;

import java.io.PrintWriter;
import javax.inject.Inject;

import org.hsqldb.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseServer extends Server {

    private static final Logger log = LoggerFactory.getLogger(DatabaseServer.class);

    @Inject
    public DatabaseServer() {
        setLogWriter(new PrintWriter(new Slf4jWriter(log::debug)));
        setErrWriter(new PrintWriter(new Slf4jWriter(log::error)));
    }
}
