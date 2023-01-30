package com.cloudshop.apigateway.config;

import com.cloudshop.apigateway.security.jwt.JwtAuthEntryPoint;
import com.cloudshop.apigateway.security.jwt.JwtTokenAuthFilter;
import com.cloudshop.apigateway.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.cloudshop.apigateway.constants.ApiGatewayConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtTokenProvider jwtTokenProvider) {
        return http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange(authorize -> authorize
                        .pathMatchers("/auth-server/**").permitAll()
                        .pathMatchers("/profile-bs/api/v1/profiles").hasAnyRole( ADMIN, MODERATOR)
                        .pathMatchers("/profile-bs/api/v1/profiles/**").hasAnyRole( ADMIN, USER)
                        .pathMatchers("/profile-bs/api/v1/profile").hasAnyRole( ADMIN, USER)
                        .anyExchange().authenticated())
                .addFilterAt(new JwtTokenAuthFilter(jwtTokenProvider), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
