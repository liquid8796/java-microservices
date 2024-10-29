package com.jarvis.rest.webservices.restful_web_services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguation {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                auth -> auth.anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
}