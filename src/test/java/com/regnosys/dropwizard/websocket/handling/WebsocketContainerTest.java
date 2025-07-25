package com.regnosys.dropwizard.websocket.handling;

import com.regnosys.dropwizard.websocket.WebsocketConfiguration;
import com.regnosys.dropwizard.websocket.registration.Endpoints;
import com.regnosys.dropwizard.websocket.registration.endpointtypes.EndpointAnnotatedJava;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebsocketContainerTest {

    @Mock
    private ServerContainer container;

    @Mock
    private WebsocketConfiguration configuration;

    @InjectMocks
    private WebsocketContainer sut;

    @Test
    public void canConstructWebsocketContainerWithServercontainer() {
        new WebsocketContainer(configuration, container);
    }

    @Test
    public void whenRegisterEndpointsIsCalledAddsEndpointsToContainer() throws DeploymentException {
        Endpoints endpoints = new Endpoints();
        endpoints.add(new EndpointAnnotatedJava(Object.class, "path"));
        endpoints.add(new EndpointAnnotatedJava(String.class, "path"));

        sut.registerEndpoints(endpoints);

        verify(container, times(2)).addEndpoint(any(Class.class));
    }

    @Test
    public void whenNoEndpointsWhereRegisteredDoesNotAddAnythingToTheContainer() throws DeploymentException {
        Endpoints endpoints = new Endpoints();

        sut.registerEndpoints(endpoints);

        verify(container, never()).addEndpoint(any(Class.class));
    }

    @Test
    public void whenDeploymentExceptionOccursWhenTryingToLoadAClassStillAddsOthers() throws DeploymentException {
        Endpoints endpoints = new Endpoints();
        endpoints.add(new EndpointAnnotatedJava(Object.class, "path"));
        endpoints.add(new EndpointAnnotatedJava(String.class, "path"));
        doThrow(DeploymentException.class).when(container).addEndpoint(Object.class);

        sut.registerEndpoints(endpoints);

        verify(container, times(2)).addEndpoint(any(Class.class));
    }

    @Test
    public void canConstructWebsocketContainerWithServercontainerAndOptions() {
        when(configuration.getAsyncSendTimeout()).thenReturn(10000L);
        when(configuration.getMaxBinaryMessageBufferSize()).thenReturn(9000);
        when(configuration.getMaxSessionIdleTimeout()).thenReturn(8000l);
        when(configuration.getMaxTextMessageBufferSize()).thenReturn(7000);
        new WebsocketContainer(configuration, container);
        verify(container, times(1)).setAsyncSendTimeout(10000L);
        verify(container, times(1)).setDefaultMaxBinaryMessageBufferSize(9000);
        verify(container, times(1)).setDefaultMaxSessionIdleTimeout(8000L);
        verify(container, times(1)).setDefaultMaxTextMessageBufferSize(7000);
    }
}
