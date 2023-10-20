package com.melihcanclk.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**").permitAll()
                                .pathMatchers("/actuator/health/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                                .pathMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/inventory-service/v3/api-docs/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/notification-service/v3/api-docs/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/order-service/v3/api-docs/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/product-service/v3/api-docs/**").permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

        return http.build();
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/product-service/v3/api-docs").and().method(HttpMethod.GET)
                        .uri("lb://product-service"))
                .route(r -> r.path("/order-service/v3/api-docs").and().method(HttpMethod.GET)
                        .uri("lb://order-service"))
                .route(r -> r.path("/inventory-service/v3/api-docs").and().method(HttpMethod.GET)
                        .uri("lb://inventory-service"))
                .build();
    }
}
