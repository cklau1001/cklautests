package com.example.testcontainer1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DBConfig {

    /*
    To enable @CreateDate and other audit aware fields
     */

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("currentUser");
    }

    /*
    @Value("${application.weather.url}")
    private String internalWeatherUrl;

    @Bean
    public String weatherUrl() { return this.internalWeatherUrl; }


     */
}
