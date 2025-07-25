package com.regnosys.dropwizard.websocket.registration.endpointtypes;

import com.regnosys.dropwizard.websocket.registration.Endpoint;

public class EndpointAnnotatedJava extends Endpoint {
    public EndpointAnnotatedJava(Class<?> endpointClass, String path) {
        super(endpointClass, EndpointType.JAVA_ANNOTATED_ENDPOINT, path);
    }
}
