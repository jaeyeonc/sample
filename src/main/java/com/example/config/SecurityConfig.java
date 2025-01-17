package com.example.config;

import com.example.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC_PATHS = {"/login", "/logout", "/**", "/css/**", "/js/**"};
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        // HttpSecurity 객체로 인증 설정을 호출
        configureAuthentication(http);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureAuthorization)
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth.requestMatchers("/upload").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .requestMatchers(PUBLIC_PATHS).permitAll()
                .anyRequest().authenticated();
    }

    public void configureAuthentication(HttpSecurity authManagerBuilder) throws Exception {
        System.out.println("configureAuthentication -------");
        authManagerBuilder.userDetailsService(customUserDetailsService);
    }
}