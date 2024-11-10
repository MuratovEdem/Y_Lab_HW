package org.example.auditablestarter.annotations;

import org.example.auditablestarter.config.AuditableConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AuditableConfiguration.class)
@Configuration
public @interface EnableAudit {
}
