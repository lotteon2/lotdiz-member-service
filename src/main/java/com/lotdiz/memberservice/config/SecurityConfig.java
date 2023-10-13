package com.lotdiz.memberservice.config;

import com.lotdiz.memberservice.exceptionhandler.JwtAccessDeniedHandler;
import com.lotdiz.memberservice.exceptionhandler.JwtAuthenticationEntryPoint;
import com.lotdiz.memberservice.filter.JwtAuthenticationFilter;
import com.lotdiz.memberservice.filter.JwtAuthorizationFilter;
import com.lotdiz.memberservice.jwt.TokenProvider;
import com.lotdiz.memberservice.jwt.handler.JwtLogoutHandler;
import com.lotdiz.memberservice.jwt.handler.JwtLogoutSuccessHandler;
import com.lotdiz.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  private final CorsFilter corsFilter;
  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtLogoutHandler jwtLogoutHandler;
  private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
  private final CorsConfig corsConfig;
  private final MemberRepository memberRepository;

  private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilter(corsFilter) // Spring Security 필터에 등록, 인증(0)
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .apply(new MyCustomDsl()) // 커스텀 필터 등록
        .and()
        .exceptionHandling(
            exceptionHandling ->
                exceptionHandling
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler))
        .headers(headers -> headers.frameOptions().sameOrigin())
        .logout(
            logout ->
                logout
                    .logoutUrl("/api/sign-out")
                    .addLogoutHandler(jwtLogoutHandler)
                    .logoutSuccessHandler(jwtLogoutSuccessHandler))
        .authorizeRequests(
            requests ->
                requests
                    .antMatchers("/api/sign-in")
                    .permitAll()
                    .antMatchers("/api/sign-up")
                    .permitAll()
                    .antMatchers("/login")
                    .permitAll()
                    .antMatchers("/api/v1/user/**")
                    .access(
                        "hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/api/v1/manager/**")
                    .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/api/v1/admin/**")
                    .access("hasRole('ROLE_ADMIN')")
                    .requestMatchers(PathRequest.toH2Console())
                    .permitAll()
                    .antMatchers("/favicon.ico")
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    return http.build();
  }

  public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
      AuthenticationManager authenticationManager =
          http.getSharedObject(AuthenticationManager.class);
      http.addFilter(corsConfig.corsFilter())
          .addFilter(new JwtAuthenticationFilter(authenticationManager, tokenProvider))
          .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository));
    }
  }
}
