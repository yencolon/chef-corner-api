package org.chefcorner.chefcorner.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationConfiguration authConfig;
    @Autowired
    JwtValidationFilter jwtValidationFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/docs", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Allow Swagger UI paths
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
