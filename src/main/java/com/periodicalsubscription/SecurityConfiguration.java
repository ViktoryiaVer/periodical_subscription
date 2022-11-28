package com.periodicalsubscription;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/login?timeout=true")
                .maximumSessions(1).and()
                .and()

                .csrf()
                .disable()

                .cors()
                .and()

                .headers()
                .frameOptions()
                .sameOrigin()
                .and()

                .authorizeRequests()
                .mvcMatchers("/", "/login/**", "/css/**", "/images/**", "/js/**", "/error",
                        "/periodicals/all", "/periodicals/{id:[\\d]+}", "/periodicals/detail*", "/cart/**", "/users/create").permitAll()
                .mvcMatchers(HttpMethod.POST, "/users/create",
                        "/cart/**", "/subscriptions/create").permitAll()
                .mvcMatchers(HttpMethod.GET, "/users/**", "/periodicals/**", "/payments/**", "/subscriptions/**").authenticated()
                .mvcMatchers(HttpMethod.POST, "/users/**", "/periodicals/**", "/payments/**", "/subscriptions/**").authenticated()
                .anyRequest().denyAll()
                .and()

                .httpBasic()
                .and()

                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/?login=true")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .authenticationProvider(authProvider())

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()

                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
