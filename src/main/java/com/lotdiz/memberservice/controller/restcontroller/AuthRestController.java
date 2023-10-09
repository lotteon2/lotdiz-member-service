package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.jwt.TokenProvider;
import com.lotdiz.memberservice.dto.MemberInfoForSignInRequestDto;
import com.lotdiz.memberservice.dto.TokenForAuthenticationResponseDto;
import com.lotdiz.memberservice.filter.JwtFilter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthRestController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenForAuthenticationResponseDto> authorize(@Valid @RequestBody
    MemberInfoForSignInRequestDto memberInfoForSignInRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(memberInfoForSignInRequestDto.getUsername(),
                memberInfoForSignInRequestDto.getPassword());

        //authenticate 메서드가 실행될 때, CustomUserDetailsService의 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);

        //보안 컨텍스트에 authentication 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //authentication 객체를 이용해 jwt token 생성
        String jwt = tokenProvider.createToken(authentication);

        //response header에 jwt token을 넣어줌 (Authorization)
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        //jwt token을 response body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenForAuthenticationResponseDto(jwt), httpHeaders,
            HttpStatus.OK);

    }
}
