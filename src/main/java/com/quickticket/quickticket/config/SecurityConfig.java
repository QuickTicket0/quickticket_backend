package com.quickticket.quickticket.config;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/error");
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 사용자입니다.");
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "이 페이지에 접근 권한이 없습니다.");
            };
}
