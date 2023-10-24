package com.lotdiz.memberservice.jwt.handler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {
  private static final Cookie EXPIRED_COOKIE = new Cookie("jwtToken", null);

  public JwtLogoutHandler() {
    setExpiredCookie();
  }

  private void setExpiredCookie() {
    EXPIRED_COOKIE.setMaxAge(-1);
    EXPIRED_COOKIE.setHttpOnly(true);
    EXPIRED_COOKIE.setPath("/");
  }

  /**
   * Securtiy Context clear
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @param authentication the current principal details
   */
  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    response.addCookie(EXPIRED_COOKIE); // remove cookie
    SecurityContextHolder.clearContext();
  }
}
