package com.quickticket.quickticket.config;

import com.quickticket.quickticket.domain.user.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                        .requestMatchers(
                                "/",
                                "/login",
                                "/signup",
                                "/findMyPassword",
                                "/findMyId"
                        ).permitAll()
                        .requestMatchers(
                                "/myPage/**",
                                "/myTicket/**",
                                "/ticketSuccess/**",
                                "/cancelTicketSuccess/**"
                        ).hasRole(UserRole.USER.getName())
                        .requestMatchers(
                                "/admin/**",
                                "/editEvent",
                                "/newEvent"
                        ).hasRole(UserRole.ADMIN.getName())
                        .anyRequest().authenticated()
            )
            .formLogin((formLogin) ->
                    formLogin
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
            )
            .logout((logoutConfig) ->
                    logoutConfig
                            .logoutSuccessUrl("/")
            )
            .exceptionHandling((exceptionConfig) ->
                exceptionConfig
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
            );

        return http.build();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {

            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {

            };
}
