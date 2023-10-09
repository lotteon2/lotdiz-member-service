package com.lotdiz.memberservice.config;

import com.lotdiz.memberservice.exceptionhandler.JwtAccessDeniedHandler;
import com.lotdiz.memberservice.exceptionhandler.JwtAuthenticationEntryPoint;
import com.lotdiz.memberservice.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(
                csrf -> csrf.disable()
            )
            .exceptionHandling(
                exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .headers(
                headers -> headers
                    .frameOptions()
                    .sameOrigin()
            )
            .sessionManagement(
                sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(
                requests -> requests
                    .antMatchers("/member-service/sign-in?type=general").permitAll()
                    .antMatchers("/member-service/sign-up").permitAll()
                    .antMatchers("/api/authenticate").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .antMatchers("/favicon.ico").permitAll()
                    .anyRequest().authenticated()
            )
            .apply(
                new JwtSecurityConfig(tokenProvider)
            );
        return httpSecurity.build();
    }
}
