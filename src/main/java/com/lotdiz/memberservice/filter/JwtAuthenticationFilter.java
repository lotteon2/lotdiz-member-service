package com.lotdiz.memberservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.memberservice.config.auth.PrincipalDetails;
import com.lotdiz.memberservice.dto.MemberInfoForSignInRequestDto;
import com.lotdiz.memberservice.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Spring Security에 UsernamePasswordAuthenticationFilter가 있음
// login 요청해서 username, password를 전송하면(post)
// UsernamePasswordAuthenticationFilter가 동작

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    logger.info("JwtAuthenticationFilter: 로그인 시도중");

    // Get username, password
    try {
      ObjectMapper om = new ObjectMapper();
      MemberInfoForSignInRequestDto signInDto =
          om.readValue(request.getInputStream(), MemberInfoForSignInRequestDto.class);
      System.out.println("signInDto: " + signInDto);

      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());

      // When authenticationManager attempts login,
      // PricipaDetailsService가 loadUserByUsername executes.
      // DB에 있는 username과 password가 일치한다.
      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      // authentication in session => success login
      PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
      logger.info("로그인 완료: " + principalDetails.getMember().getMemberEmail());

      return authentication; // save in session, 권한 관리를 Spring Security가 대신 해줌
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  // attemptAuthentication 실행 후 인증이 정상적으로 되었을 때 successfulAuthentication 함수 실행
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    logger.info("successfulAuthentication 실행됨: 인증 완료 후");
    //    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
    String jwtToken = tokenProvider.createToken(authResult);

    response.addHeader("Authorization", "Bearer " + jwtToken);
  }
}
