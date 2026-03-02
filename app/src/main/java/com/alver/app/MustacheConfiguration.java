package com.alver.app;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MustacheConfiguration {

    @Bean
    MustacheFactory getMustacheFactory() {
        DefaultMustacheFactory factory = new DefaultMustacheFactory();
        factory.setRecursionLimit(10);
        return factory;
    }

}
