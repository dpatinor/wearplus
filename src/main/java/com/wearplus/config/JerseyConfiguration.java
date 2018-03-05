package com.wearplus.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import com.wearplus.web.rest.ClothesResource;
import com.wearplus.web.rest.ShoppingResource;
import com.wearplus.web.rest.TypeClothesResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("rest")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {}

    @PostConstruct
    public void setUp() {
        register(ShoppingResource.class);
        register(TypeClothesResource.class);
        register(ClothesResource.class);
        register(GenericExceptionMapper.class);
    }
}
