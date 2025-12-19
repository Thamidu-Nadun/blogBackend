package com.nadun.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nadun.blog.service.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to authentication endpoints
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/users/verify/**").permitAll()
                        .requestMatchers("/api/v1/auth/me").authenticated()
                        .requestMatchers("/api/v1/auth/logout").authenticated()

                        // GET Public Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/articles/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()

                        // App Endpoints
                        .requestMatchers("/api/v1/status/**")
                        .hasAnyRole("ADMIN")

                        // Article endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/articles/save")
                        .hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/articles/update/**")
                        .hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/articles/delete/**")
                        .hasAnyRole("ADMIN", "AUTHOR")

                        // Category endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/save")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/update/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/delete/**")
                        .hasRole("ADMIN")

                        // Comment endpoints
                        // todo: add comment endpoint

                        // Tag endpoints
                        .requestMatchers(HttpMethod.POST, "/api/v1/tags/**")
                        .hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tags/**")
                        .hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tags/**")
                        .hasAnyRole("ADMIN", "AUTHOR")

                        // User endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**")
                        .hasAnyRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
