package be.tomcools.dropwizard.websocket.handling;


import io.dropwizard.jetty.MutableServletContextHandler;
import org.eclipse.jetty.websocket.javax.server.config.JavaxWebSocketServletContainerInitializer;

public class WebsocketContainerInitializer {
    public void initialize(MutableServletContextHandler contextHandler,
                           JavaxWebSocketServletContainerInitializer.Configurator configurator) {
        try {
            JavaxWebSocketServletContainerInitializer.configure(contextHandler, configurator);
        } catch (Exception e) {
            throw new IllegalStateException("Could not initialize context handler to enable Websockets", e);
        }
    }
}
