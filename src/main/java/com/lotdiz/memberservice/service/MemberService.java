package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.MemberInfoForSignInRequestDto;
import com.lotdiz.memberservice.dto.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.jwt.TokenProvider;
import com.lotdiz.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final Logger logger = LoggerFactory.getLogger(MemberService.class);
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;

  /**
   * 회원가입
   *
   * @param memberDto
   * @return
   */
  public Member signup(MemberInfoForSignUpRequestDto memberDto) {
    if (memberRepository.findByMemberEmail(memberDto.getMemberEmail()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 회원입니다.");
    }

    Member member =
        Member.builder()
            .memberEmail(memberDto.getMemberEmail())
            .memberPassword(passwordEncoder.encode(memberDto.getMemberPassword()))
            .memberName(memberDto.getMemberName())
            .memberPhoneNumber(memberDto.getMemberPhoneNumber())
            .memberPrivacyAgreement(memberDto.getMemberPrivacyAgreement())
            .build();
    return memberRepository.save(member);
  }

  public String signin(MemberInfoForSignInRequestDto memberDto) {
    logger.info("sign-in Service Logic..");
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());

    // loadUserByUsername in PrincipalDetailsService executes.
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    String token = tokenProvider.createToken(authentication);
    return token;
  }
}
