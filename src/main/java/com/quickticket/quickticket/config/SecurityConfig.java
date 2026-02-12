package com.quickticket.quickticket.config;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                                "/css/**",
                                "/images/**"
                        ).permitAll()
                        .requestMatchers(
                                "/myPage/**",
                                "/myTicket/**",
                                "/ticketSuccess/**",
                                "/cancelTicketSuccess/**"
                        ).hasRole(AccountType.USER.getName())
                        .requestMatchers(
                                "/admin/**",
                                "/editEvent",
                                "/newEvent"
                        ).hasRole(AccountType.ADMIN.getName())
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
                            .logoutUrl("/api/account/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true)
            )
            .exceptionHandling((exceptionConfig) ->
                exceptionConfig
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
            );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {

            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {

            };
}
