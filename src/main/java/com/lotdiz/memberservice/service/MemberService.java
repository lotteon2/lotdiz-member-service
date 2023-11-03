package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.MemberNameDto;
import com.lotdiz.memberservice.dto.request.*;
import com.lotdiz.memberservice.dto.response.MemberInfoForProjectResponseDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.dto.response.MemberNameResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.exception.AlreadyRegisteredMemberException;
import com.lotdiz.memberservice.exception.InsufficientPointsException;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.messagequeue.MemberProducer;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.utils.CustomErrorMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberProducer memberProducer;
  private final PasswordEncoder passwordEncoder;

  /**
   * 회원가입
   *
   * @param memberSignUpDto
   */
  @Transactional
  public void signup(MemberInfoForSignUpRequestDto memberSignUpDto) {
    if (memberRepository.findByMemberEmail(memberSignUpDto.getUsername()).orElse(null) != null) {
      throw new AlreadyRegisteredMemberException();
    }
    Member savedMember = memberRepository.save(Member.signup(memberSignUpDto));
    memberProducer.sendCreateMember(
        CreateMemberRequestDto.builder()
            .memberId(savedMember.getMemberId())
            .memberRole(savedMember.getMemberRole().getValue())
            .memberEmail(savedMember.getMemberEmail())
            .memberPhoneNumber(savedMember.getMemberPhoneNumber())
            .memberName(savedMember.getMemberName())
            .createdAt(savedMember.getCreatedAt())
            .build());
  }

  /**
   * 회원 정보 수정
   *
   * @param email
   * @param memberChangeDto
   */
  @Transactional
  public void renewMemberInfo(String email, MemberInfoForChangeRequestDto memberChangeDto) {
    Member member =
        memberRepository
            .findByMemberEmail(email)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    Member.renew(member, memberChangeDto);
  }

  /**
   * 회원 정보 조회
   *
   * @param memberId
   * @return MemberInfoForQueryResponseDto
   */
  public MemberInfoForQueryResponseDto showMember(Long memberId) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    return CustomMapper.MemberInfoForQueryResponseDtoMapper(member);
  }

  /**
   * Project-service 에서 쓰일 멤버 이름, 프로필 이미지 url 가져오기
   *
   * @param memberIds
   * @return Map<String, MemberInfoForProjectResponseDto>
   */
  public Map<String, MemberInfoForProjectResponseDto> getNameAndProfileImage(List<Long> memberIds) {
    Map<String, MemberInfoForProjectResponseDto> memberInfos = new HashMap<>();
    for (Long memberId : memberIds) {
      Member member = findMemberByMemberId(memberId);
      MemberInfoForProjectResponseDto memberInfoDto =
          MemberInfoForProjectResponseDto.builder()
              .memberName(member.getMemberName())
              .memberProfileImageUrl(member.getMemberProfileImageUrl())
              .build();
      memberInfos.put(memberId.toString(), memberInfoDto);
    }
    return memberInfos;
  }

  /**
   * 포인트 환불
   *
   * @param refundDto
   */
  @Transactional
  public void refundPoints(PointInfoForRefundRequestDto refundDto) {
    Member member = findMemberByMemberId(refundDto.getMemberId());
    member.assignMemberPoint(member.getMemberPoint() + refundDto.getMemberPoint());
    memberRepository.save(member);
  }

  /**
   * 포인트 차감
   *
   * @param pointConsumptionDto
   */
  @Transactional
  public void consumePoints(PointInfoForConsumptionRequestDto pointConsumptionDto) {
    Member member = findMemberByMemberId(pointConsumptionDto.getMemberId());
    if (member.getMemberPoint() < pointConsumptionDto.getMemberPoint()) {
      throw new InsufficientPointsException();
    }
    member.assignMemberPoint(member.getMemberPoint() - pointConsumptionDto.getMemberPoint());
  }

  /**
   * 멤버십 해제
   *
   * @param memberId
   */
  @Transactional
  public void breakMembership(Long memberId) {
    Member member = findMemberByMemberId(memberId);
    Membership membership = member.getMembership();
    membership.assignMembershipStatus(false);
  }

  public Member findMemberByMemberId(Long memberId) {
    return memberRepository
        .findByMemberId(memberId)
        .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
  }

  public Boolean checkMemberByMemberEmail(String memberEmail) {
    Member member = memberRepository.findByMemberEmail(memberEmail).orElse(null);
    return member != null;
  }

  public MemberNameResponseDto getMemberNames(List<Long> memberIds) {
    List<Member> membersNameByIds = memberRepository.findMembersNameByIds(memberIds);
    List<MemberNameDto> memberNameDtos = new ArrayList<>();
    membersNameByIds.forEach(
        item ->
            memberNameDtos.add(
                MemberNameDto.builder()
                    .memberId(item.getMemberId())
                    .memberName(item.getMemberName())
                    .build()));
    return MemberNameResponseDto.builder().memberNameDtos(memberNameDtos).build();
  }

  /**
   * 회원 포인트 조회
   *
   * @param memberId
   * @return Long
   */
  public Long getMemberPoints(Long memberId) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    return member.getMemberPoint();
  }

  public Boolean checkOriginPassword(Long memberId, String originPassword) {
    log.info("originPassword: " + originPassword);
    originPassword = originPassword.replaceAll("\"", "");
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    if (passwordEncoder.matches(originPassword, member.getMemberPassword())) {
      return true;
    }
    return false;
  }
}
