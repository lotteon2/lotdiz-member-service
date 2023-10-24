package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.request.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.dto.request.PointInfoForConsumptionRequestDto;
import com.lotdiz.memberservice.dto.request.PointInfoForRefundRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForProjectResponseDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.exception.AlreadyRegisteredMemberException;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.utils.CustomErrorMessage;
import java.rmi.AlreadyBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final Logger logger = LoggerFactory.getLogger(MemberService.class);
  private final MemberRepository memberRepository;
  private final MembershipService membershipService;

  /**
   * 회원가입
   *
   * @param memberSignUpDto
   * @return
   */
  @Transactional
  public Member signup(MemberInfoForSignUpRequestDto memberSignUpDto) {
    if (memberRepository.findByMemberEmail(memberSignUpDto.getUsername()).orElse(null) != null) {
      throw new AlreadyRegisteredMemberException();
    }
    return memberRepository.save(Member.signup(memberSignUpDto));
  }

  @Transactional
  public void renew(String email, MemberInfoForChangeRequestDto memberChangeDto) {
    Member member =
        memberRepository
            .findByMemberEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    Member.renew(member, memberChangeDto);
  }

  public MemberInfoForQueryResponseDto showMember(Long memberId) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    return CustomMapper.MemberInfoForQueryResponseDtoMapper(member);
  }

  public Member findMemberByMemberId(Long memberId) {
    return memberRepository.findByMemberId(memberId).orElseThrow();
  }

  public Map<String, MemberInfoForProjectResponseDto> inquireNameAndProfileImage(
      List<Long> memberIds) {
    Map<String, MemberInfoForProjectResponseDto> memberInfos = new HashMap<>();
    for (Long memberId : memberIds) {
      Member member =
          memberRepository
              .findByMemberId(memberId)
              .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
      MemberInfoForProjectResponseDto memberInfoDto =
          MemberInfoForProjectResponseDto.builder()
              .memberName(member.getMemberName())
              .memberProfileImageUrl(member.getMemberProfileImageUrl())
              .build();
      memberInfos.put(memberId.toString(), memberInfoDto);
    }
    return memberInfos;
  }

  public void refund(PointInfoForRefundRequestDto refundDto) {
    Member member =
        memberRepository
            .findByMemberId(Long.valueOf(refundDto.getMemberId()))
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    member.assignMemberPoint(member.getMemberPoint() + refundDto.getMemberPoint());
    memberRepository.save(member);
  }

  public void consume(PointInfoForConsumptionRequestDto pointConsumptionDto) {
    Member member =
        memberRepository.findByMemberId(pointConsumptionDto.getMemberId()).orElseThrow();
    if (member.getMemberPoint() < pointConsumptionDto.getMemberPoint()) {
      throw new RuntimeException("포인트가 부족합니다.");
    }
    member.assignMemberPoint(member.getMemberPoint() - pointConsumptionDto.getMemberPoint());
  }

  public Member findMemberByMemberId(Long memberId) {
    return memberRepository.findByMemberId(memberId).orElseThrow();
  }

  @Transactional
  public void breakMembership(Long memberId, Long membershipId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
    member.assignMembershipId(null);
    Membership membership = membershipService.find(membershipId);
    membership.assignMembershipStatus(false);
  }
}
