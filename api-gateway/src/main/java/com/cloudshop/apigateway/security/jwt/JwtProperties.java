package com.cloudshop.apigateway.security.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private long validityInMs; // 1h
}
