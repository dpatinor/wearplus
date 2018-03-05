package com.wearplus.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import com.wearplus.web.rest.ShoppingResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("rest")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {}

    @PostConstruct
    public void setUp() {
        register(ShoppingResource.class);
        register(GenericExceptionMapper.class);
    }
}
