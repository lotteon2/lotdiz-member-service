package com.lotdiz.memberservice.config;

import com.lotdiz.memberservice.filter.JwtFilter;
import com.lotdiz.memberservice.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    /**
     * tokenProvider를 주입받아 JwtFilter를 통해 Security 로직에 필터를 등록
     * @param httpSecurity
     */
    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(
            new JwtFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class
        );
    }
}
