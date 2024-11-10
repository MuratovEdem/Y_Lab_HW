package org.example.auditablestarter.config;

import org.example.auditablestarter.aspect.AuditableAspect;
import org.springframework.context.annotation.Bean;

public class AuditableConfiguration {

    @Bean
    public AuditableAspect auditableConfig() {
        return new AuditableAspect();
    }
}
