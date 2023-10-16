package com.lotdiz.memberservice.filter;

import com.lotdiz.memberservice.config.auth.PrincipalDetails;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Security가 filter를 가지고 있는데 BasicAuthenticationFilter가 있다.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때, 위 필터를 무조건 타게 되어있음.
// 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탄다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private String secret;

  private MemberRepository memberRepository;
  private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  public JwtAuthorizationFilter(
      AuthenticationManager authenticationManager, MemberRepository memberRepository, String secret) {
    super(authenticationManager);
    this.memberRepository = memberRepository;
    this.secret = secret;
  }

  /**
   * 인증이나 권한이 필요한 주소요청이 있을 때 해당 filter를 타게됨
   *
   * @param request
   * @param response
   * @param chain
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    logger.info("인증이나 권한이 필요한 주소 요청이 됨.");

    String jwtHeader = request.getHeader("Authorization");
    logger.info("jwtHeader: " + jwtHeader);

    // jwt token 검증.

    // header가 있는지 확인
    if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
      chain.doFilter(request, response);
      return;
    }

    // jwt token을 검증해서 정상적인 사용자인지 확인
    String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

    byte[] keyBytes = Decoders.BASE64.decode(secret);
    SecretKey key = Keys.hmacShaKeyFor(keyBytes);
    Claims claims =
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
    String username = claims.get("username", String.class);
    String auth = claims.get("auth", String.class);
    int memberId = claims.get("memberId", Integer.class);
    logger.info("username: " + username);
    logger.info("auth: " + auth);
    logger.info("memberId: " + memberId);
    // 서명이 정상적으로 됨
    if (username != null) {
      logger.info("서명 정상 인증");
      Optional<Member> om = memberRepository.findByMemberEmail(username);
      if (om.isPresent()) {
        Member member = om.get();

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        // jwt token 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities());

        // Security Session에 접근하여 Authentication객체를 저장.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
      }
    }
  }
}
