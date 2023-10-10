package com.lotdiz.memberservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotdiz.memberservice.dto.MemberInfoForSignInRequestDto;
import com.lotdiz.memberservice.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final TokenProvider tokenProvider;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
        new AntPathRequestMatcher("/member-service/sign-in", HttpMethod.POST.name());

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
        TokenProvider tokenProvider) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        if (isAuthorized()) {
            throw new RuntimeException("이미 인증된 사용자 입니다.");
        }

        // 클라이언트 요청 검증
        try {
            MemberInfoForSignInRequestDto memberInfoForSignInRequestDto = objectMapper.readValue(request.getReader(),
                MemberInfoForSignInRequestDto.class);
        } catch (IOException e) {
            throw new RuntimeException("잘못된 JSON 요청 형식입니다.");
        }

        // Authentication Logic start.
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        Authentication authentication = null;
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(),
                requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
        }
        // Authentication Logic end.

        return this.getAuthenticationManager().authenticate(authentication);
    }

    /**
     * Request Header에서 token 정보를 꺼내오기
     *
     * @param request
     * @return token(String)
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * 이미 인증된 사용자인지 검증
     *
     * @return boolean
     */
    private boolean isAuthorized() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            return false;
        }

        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return false;
        }

        return true;
    }
}
