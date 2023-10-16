package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.jwt.TokenProvider;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
   * @param memberSignUpDto
   * @return
   */
  public Member signup(MemberInfoForSignUpRequestDto memberSignUpDto) {
    if (memberRepository.findByMemberEmail(memberSignUpDto.getMemberEmail()).orElse(null) != null) {
      throw new RuntimeException("이미 가입되어 있는 회원입니다.");
    }
    return memberRepository.save(Member.signup(memberSignUpDto));
  }

  @Transactional
  public void renew(String email, MemberInfoForChangeRequestDto memberChangeDto) {
    Member member = memberRepository.findByMemberEmail(email).orElseThrow();
    memberRepository.save(Member.renew(member, memberChangeDto));
  }

  public MemberInfoForQueryResponseDto showMember(Long memberId) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new RuntimeException("해당 회원을 조회할 수 없습니다"));
    return CustomMapper.MemberInfoForQueryResponseDtoMapper(member);
  }
}
