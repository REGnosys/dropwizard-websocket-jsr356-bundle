package com.regnosys.dropwizard.websocket.handling;

import com.regnosys.dropwizard.websocket.WebsocketHandler;
import io.dropwizard.core.server.ServerFactory;
import io.dropwizard.core.setup.Environment;
import org.eclipse.jetty.server.Server;

public class ServerFactoryWrapper implements ServerFactory {
    private final WebsocketHandler handler;
    private final ServerFactory serverFactory;

    public ServerFactoryWrapper(ServerFactory serverFactory, WebsocketHandler handler) {
        this.serverFactory = serverFactory;
        this.handler = handler;
    }

    @Override
    public Server build(Environment environment) {
        Server server = serverFactory.build(environment);
        environment.getApplicationContext().setServer(server);
        environment.getAdminContext().setServer(server);
        handler.initialize();
        return server;
    }

    @Override
    public void configure(Environment environment) {
        //nothing
    }
}
