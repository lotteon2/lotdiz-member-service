package com.lotdiz.memberservice.jwt;

import com.lotdiz.memberservice.config.auth.PrincipalDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    logger.info(
        "principalDetails.getMember().getMemberId(): "
            + principalDetails.getMember().getMemberId());
    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .claim("memberId", principalDetails.getMember().getMemberId().toString())
        .claim("username", principalDetails.getMember().getMemberEmail())
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();
  }
}
