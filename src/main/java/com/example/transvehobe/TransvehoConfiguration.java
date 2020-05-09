package com.example.transvehobe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;

@Configuration
@ComponentScan("com.example.transvehobe")
public class TransvehoConfiguration {

    @Bean
    public CollectionAwareProjectionFactory projectionFactory() {
        return new CollectionAwareProjectionFactory();
    }
}
