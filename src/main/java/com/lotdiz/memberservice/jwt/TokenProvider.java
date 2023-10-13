package com.lotdiz.memberservice.jwt;

import com.lotdiz.memberservice.config.auth.PrincipalDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements InitializingBean {

  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
  private static final String AUTHORITIES_KEY = "auth";
  private final String secret;
  private final Long tokenValidityInMilliseconds;
  private Key key;

  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds) {
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * authentication 객체를 권한정보를 이용해서 token 생성
   *
   * @param authentication
   * @return jwt token
   */
  public String createToken(Authentication authentication) {
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);
    logger.info("authentication.getName(): " + authentication.getName());
    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .claim("memberId", principalDetails.getMember().getMemberId())
        .claim("username", principalDetails.getMember().getMemberEmail())
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();
  }

  /**
   * jwt token으로 클레임을 만들고, user객체를 만들어 authentication 객체 생성 및 반환
   *
   * @param token
   * @return authentication
   */
//  public Authentication getAuthentication(String token) {
//    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//
//    List<GrantedAuthority> authorities =
//        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());
//
//    User principal = new User(claims.getSubject(), "", authorities);
//
//    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//  }

  /**
   * jwt token의 유효성 검증
   *
   * @param token
   * @return boolean
   */
//  public boolean validateToken(String token) {
//    try {
//      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//      return true;
//    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//      logger.info("잘못된 JWT 서명입니다.");
//    } catch (ExpiredJwtException e) {
//      logger.info("만료된 JWT 토큰입니다.");
//    } catch (UnsupportedJwtException e) {
//      logger.info("지원되지 않는 JWT 토큰입니다.");
//    } catch (IllegalArgumentException e) {
//      logger.info("JWT 토큰이 잘못되었습니다.");
//    }
//    return false;
//  }
}
