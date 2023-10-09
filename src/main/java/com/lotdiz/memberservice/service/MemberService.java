package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.entity.Authority;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.MemberRole;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.utils.SecurityUtil;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param memberDto
     * @return
     */
    public Member signup(MemberInfoForSignUpRequestDto memberDto) {
        if (memberRepository.findByMemberEmail(memberDto.getMemberEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }

        Authority authority = Authority.builder()
            .authorityName("ROLE_SUPPORTER")
            .build();

        Member member = Member.builder()
            .memberEmail(memberDto.getMemberEmail())
            .memberPassword(passwordEncoder.encode(memberDto.getMemberPassword()))
            .memberName(memberDto.getMemberName())
            .memberPhoneNumber(memberDto.getMemberPhoneNumber())
            .memberPrivacyAgreement(memberDto.getMemberPrivacyAgreement())
            .authorities(Collections.singleton(authority))
            .build();
        return memberRepository.save(member);
    }

    /**
     * memberEmail을 기준으로 유저 및 권한 정보 가져오기
     * @param memberEmail
     * @return Member
     */
    public Optional<Member> getMemberWithAuthorities(String memberEmail) {
        return memberRepository.findOneWithAuthoritiesByMemberEmail(memberEmail);
    }

    /**
     * Security Context에 저장된 memberEmail을 기준으로 유저 정보 가져오기
     * @return Member
     */
    public Optional<Member> getMyMemberWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(memberRepository::findOneWithAuthoritiesByMemberEmail);
    }
}
