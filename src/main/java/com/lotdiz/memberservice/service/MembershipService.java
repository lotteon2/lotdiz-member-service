package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinRequestDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.KakaoPayReadyForMemberResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.entity.MembershipPolicy;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.repository.MembershipPolicyRepository;
import com.lotdiz.memberservice.repository.MembershipRepository;
import com.lotdiz.memberservice.service.client.PaymentsClientService;
import com.lotdiz.memberservice.utils.CustomErrorMessage;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {

  private final MemberRepository memberRepository;
  private final MembershipRepository membershipRepository;
  private final PaymentsClientService paymentsClientService;
  private final MembershipPolicyRepository membershipPolicyRepository;

  /**
   * 멤버십 생성 (미완성), 결제 후 추가 정보 업데이트 필요
   *
   * @param memberId
   * @param membershipJoinDto
   */
  @Transactional
  public String createMembership(Long memberId, MembershipInfoForJoinRequestDto membershipJoinDto) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));

    MembershipPolicy membershipPolicy =
        membershipPolicyRepository.findByMembershipPolicyId(
            membershipJoinDto.getMembershipPolicyId());

    Membership membership = Membership.builder().membershipPolicy(membershipPolicy).build();
    Membership saved = membershipRepository.save(membership);
    member.assignMembership(saved);

    PaymentsInfoForKakaoPayRequestDto paymentsDto =
        CustomMapper.PaymentsInfoForKakoaPayRequestDtoMapper(
            saved.getMembershipId(), membershipJoinDto);

    KakaoPayReadyForMemberResponseDto kakaoPayReadyForMemberDto =
        paymentsClientService.getMembershipSubscription(paymentsDto);
    saved.assignMembershipSubscriptionId(kakaoPayReadyForMemberDto.getMembershipSubscriptionId());
    return kakaoPayReadyForMemberDto.getNext_redirect_pc_url();
  }

  /**
   * 결제 후 멤버십에 추가 정보 업데이트
   *
   * @param membershipAssignDto
   */
  @Transactional
  public void joinMembershipComplete(MembershipInfoForAssignRequestDto membershipAssignDto) {
    LocalDateTime membershipSubscriptionCreatedAt = membershipAssignDto.getCreatedAt();
    LocalDateTime membershipSubscriptionExpiredAt =
        membershipSubscriptionCreatedAt.withYear(membershipSubscriptionCreatedAt.getYear() + 1);

    Membership found =
        membershipRepository
            .findByMembershipId(membershipAssignDto.getMembershipId())
            .orElseThrow(
                () -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBERSHIP));

    if (found == null) {
      throw new EntityNotFoundException(CustomErrorMessage.NO_MEMBERSHIP);
    }

    Membership.addMore(found, membershipSubscriptionCreatedAt, membershipSubscriptionExpiredAt);
  }
}
