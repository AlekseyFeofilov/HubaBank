package ru.hubabank.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.hubabank.core.security.InternalAuthenticationConverter;
import ru.hubabank.core.security.InternalAuthenticationFilter;
import ru.hubabank.core.security.UserAuthenticationConverter;
import ru.hubabank.core.security.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private static final RequestMatcher[] requestMatchers = {
            new AntPathRequestMatcher("/internal/**"),
            new AntPathRequestMatcher("/core/api/v*/internal/**")
    };

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserAuthenticationConverter userAuthenticationConverter,
            InternalAuthenticationConverter internalAuthenticationConverter
    ) throws Exception {
        return http
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/core/api/**",
                                "/health"
                        ).permitAll()
                        .requestMatchers(
                                "/core/api/v1/**"
                        ).authenticated()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(new InternalAuthenticationFilter(
                        internalAuthenticationConverter,
                        requestMatchers
                ), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new UserAuthenticationFilter(
                        userAuthenticationConverter,
                        requestMatchers
                ), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
