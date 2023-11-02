package com.lotdiz.memberservice.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final String LOGOUT_REDIRECT_END_POINT = "/api/sign-in";

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

    objectMapper.writeValue(
        response.getWriter(),
        new ResultDataResponse<>(
            String.valueOf(HttpStatus.OK.value()),
            HttpStatus.OK.name(),
            "로그아웃 성공",
            null));
  }
}
