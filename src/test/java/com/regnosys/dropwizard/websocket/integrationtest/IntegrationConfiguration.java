package com.regnosys.dropwizard.websocket.integrationtest;

import com.regnosys.dropwizard.websocket.WebsocketBundleConfiguration;
import com.regnosys.dropwizard.websocket.WebsocketConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class IntegrationConfiguration extends Configuration implements WebsocketBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final WebsocketConfiguration websocketConfiguration = new WebsocketConfiguration();

    @Override
    public WebsocketConfiguration getWebsocketConfiguration() {
        return websocketConfiguration;
    }
}
