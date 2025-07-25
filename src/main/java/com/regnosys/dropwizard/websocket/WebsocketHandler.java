package com.regnosys.dropwizard.websocket;

import com.regnosys.dropwizard.websocket.handling.WebsocketContainer;
import com.regnosys.dropwizard.websocket.handling.WebsocketContainerInitializer;
import com.regnosys.dropwizard.websocket.registration.EndpointRegistration;
import io.dropwizard.core.setup.Environment;
import javax.servlet.ServletContext;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.websocket.javax.server.config.JavaxWebSocketServletContainerInitializer;

public class WebsocketHandler implements JavaxWebSocketServletContainerInitializer.Configurator {
    private final EndpointRegistration endpointRegistration;
    private final WebsocketConfiguration configuration;
    private final Environment environment;
    private final WebsocketContainerInitializer containerInitializer;

    public WebsocketHandler(WebsocketConfiguration configuration, Environment environment) {
        this(configuration, environment, new EndpointRegistration(), new WebsocketContainerInitializer());
    }

    public WebsocketHandler(WebsocketConfiguration configuration, Environment environment, EndpointRegistration endpointRegistration, WebsocketContainerInitializer containerInitializer) {
        this.configuration = configuration;
        this.environment = environment;
        this.endpointRegistration = endpointRegistration;
        this.containerInitializer = containerInitializer;
    }

    public void addEndpoint(Class<?> endpointClass) {
        this.endpointRegistration.add(endpointClass);
    }

    public void addEndpoint(ServerEndpointConfig serverEndpointConfig) {
        this.endpointRegistration.add(serverEndpointConfig);
    }

    public void initialize() {        
        containerInitializer.initialize(environment.getApplicationContext(), this);
    }

    @Override
    public void accept(ServletContext servletContext, ServerContainer serverContainer) {
        WebsocketContainer container = new WebsocketContainer(configuration, serverContainer);
        container.registerEndpoints(endpointRegistration.getRegisteredEndpoints());
    }
}
