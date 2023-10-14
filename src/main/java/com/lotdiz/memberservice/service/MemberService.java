package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.jwt.TokenProvider;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
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


  public void renew(MemberInfoForChangeRequestDto memberChangeDto) {
    Member member = memberRepository.findByMemberId(memberChangeDto.getMemberId()).orElseThrow();
    member = Member.builder()
        .memberName(memberChangeDto.getMemberName())
        .memberPhoneNumber(memberChangeDto.getMemberPhoneNumber())
        .memberProfileImageUrl(memberChangeDto.getMemberProfileImageUrl())
        .build();

        

  }

  public MemberInfoForQueryResponseDto showMember(Long memberId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new RuntimeException("해당 회원정보를 조회할 수 없습니다"));
    return CustomMapper.MemberInfoForQueryResponseDtoMapper(member);
  }

//  public Boolean signin(MemberInfoForSignInRequestDto memberDto) {
//    logger.info("sign-in Service Logic..");
//    Optional<Member> om = memberRepository.findByMemberEmail(memberDto.getPassword());
//    if(om.isPresent()) {
//      Member member = om.get();
//      String encryptedInDB = member.getMemberPassword();
//      String encryptedInput = passwordEncoder.encode(memberDto.getPassword());
//      if(encryptedInput.equals(encryptedInDB)) {
//        logger.info("비밀번호가 일치합니다.");
//        return true;
//      }else {
//        logger.info("비밀번호가 일치하지 않습니다.");
//      }
//    }
//    return false;
//  }
}
