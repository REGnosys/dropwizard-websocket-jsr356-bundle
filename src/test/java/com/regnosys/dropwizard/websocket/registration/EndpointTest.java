package com.regnosys.dropwizard.websocket.registration;

import com.regnosys.dropwizard.websocket.registration.endpointtypes.EndpointAnnotatedJava;
import com.regnosys.dropwizard.websocket.registration.endpointtypes.EndpointProgrammaticJava;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;;

public class EndpointTest {

    @Test
    public void verifyEqualsIsCorrectlyInheritedByProgrammaticJava() {
        EqualsVerifier.forClass(Endpoint.class).withRedefinedSubclass(EndpointProgrammaticJava.class);
    }

    @Test
    public void verifyEqualsIsCorrectlyInheritedByAnnotatedJava() {
        EqualsVerifier.forClass(Endpoint.class).withRedefinedSubclass(EndpointAnnotatedJava.class);
    }
}
