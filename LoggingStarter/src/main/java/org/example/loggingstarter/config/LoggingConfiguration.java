package org.example.loggingstarter.config;

import org.example.loggingstarter.aspect.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    public LoggingAspect loggingConfig() {
        return new LoggingAspect();
    }
}
