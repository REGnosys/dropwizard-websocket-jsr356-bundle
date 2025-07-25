package com.regnosys.dropwizard.websocket.integrationtest;

import com.regnosys.dropwizard.websocket.WebsocketBundle;
import com.regnosys.dropwizard.websocket.integrationtest.annotatedjavaee.PingPongServerEndpoint;
import com.regnosys.dropwizard.websocket.integrationtest.programmaticjavaee.ProgrammaticServerEndpoint;
import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import javax.websocket.server.ServerEndpointConfig;

public class IntegrationTestApplication extends Application<IntegrationConfiguration> {
    private WebsocketBundle websocket = new WebsocketBundle();

    @Override
    public void initialize(Bootstrap<IntegrationConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(websocket);
    }

    @Override
    public void run(IntegrationConfiguration configuration, Environment environment) throws Exception {
        //Annotated endpoint
        websocket.addEndpoint(PingPongServerEndpoint.class);

        //programmatic endpoint
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(ProgrammaticServerEndpoint.class, "/programmatic").build();
        websocket.addEndpoint(serverEndpointConfig);

        // healthcheck to keep output quiet
        environment.healthChecks().register("healthy", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
    }
}
