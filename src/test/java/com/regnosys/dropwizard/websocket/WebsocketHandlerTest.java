package com.regnosys.dropwizard.websocket;

import com.regnosys.dropwizard.websocket.handling.WebsocketContainerInitializer;
import com.regnosys.dropwizard.websocket.registration.EndpointRegistration;
import com.regnosys.dropwizard.websocket.registration.Endpoints;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.setup.Environment;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebsocketHandlerTest {

    private final Environment environment = mock(Environment.class, RETURNS_DEEP_STUBS);
    private final WebsocketBundleConfiguration configuration = (WebsocketBundleConfiguration)mock(Configuration.class,
            withSettings().defaultAnswer(RETURNS_DEEP_STUBS).extraInterfaces(WebsocketBundleConfiguration.class));

    @Mock
    private EndpointRegistration endpointRegistration;

    @Mock
    private WebsocketConfiguration wsConfiguration;

    @Mock
    private WebsocketContainerInitializer containerInitializer;

    @Mock
    private Endpoints endpoints;

    @InjectMocks
    private WebsocketHandler sut;

    @BeforeEach
    void init() {
        lenient().when(endpointRegistration.getRegisteredEndpoints()).thenReturn(endpoints);
    }

    @Test
    void canConstructHandlerWithEnvironment() {
        new WebsocketHandler(configuration.getWebsocketConfiguration(), environment);
    }

    @Test
    void whenAddAnnotatedEndpointIsCalledPassesObjectToEndpointRegistration() {
        sut.addEndpoint(TestEndpoint.class);

        verify(endpointRegistration).add(TestEndpoint.class);
    }

    @Test
    void whenAddProgrammaticEndpointIsCalledPassesObjectToEndpointRegistration() {
        ServerEndpointConfig config = ServerEndpointConfig.Builder.create(TestEndpoint.class, "/path").build();
        sut.addEndpoint(config);

        verify(endpointRegistration).add(config);
    }

    @Test
    void whenInitializeIsCalled_InitializesWebsocketContainer() {
        sut.initialize();

        verify(containerInitializer).initialize(environment.getApplicationContext(), sut);   
    }

    @ServerEndpoint("/chat")
    class TestEndpoint {
        @OnOpen
        public void open(Session session) {
        }

        @OnClose
        public void close(Session session) {
        }

        @OnError
        void onError(Throwable error) {
        }

        @OnMessage
        public void handleMessage(String message, Session session) {
        }
    }

}
