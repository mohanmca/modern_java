package com.commonda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${http.endpoint}")
    private String httpEndpoint;

    @Autowired
    private WebClient.Builder builder;

    @Bean
    public WebClient getTestClient() {
        assert httpEndpoint != null;
        return builder.baseUrl(httpEndpoint).build();
    }


}
