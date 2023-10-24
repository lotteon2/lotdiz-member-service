package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.exception.NoMembershipException;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
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

  /**
   * 멤버십 생성 (미완성), 결제 후 추가 정보 업데이트 필요
   *
   * @param memberId
   * @param membershipJoinDto
   */
  @Transactional
  public void createMembership(Long memberId, MembershipInfoForJoinReqeustDto membershipJoinDto) {
    Member member =
        memberRepository
            .findByMemberId(memberId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBER));
    Membership membership =
        Membership.builder().membershipPolicyId(membershipJoinDto.getMembershipPolicyId()).build();
    Membership saved = membershipRepository.save(membership);
    member.assignMembershipId(saved.getMembershipId());
    PaymentsInfoForKakaoPayRequestDto paymentsDto =
        CustomMapper.PaymentsInfoForKakoaPayRequestDtoMapper(
            saved.getMembershipId(), membershipJoinDto);
    Long membershipSubscriptionId = paymentsClientService.getMembershipSubscription(paymentsDto);
    saved.assignMembershipSubscriptionId(membershipSubscriptionId);
  }

  /**
   * 결제 후 멤버십에 추가 정보 업데이트
   *
   * @param membershipAssignDto
   */
  public void joinMembershipComplete(MembershipInfoForAssignRequestDto membershipAssignDto) {
    LocalDateTime membershipSubscriptionCreatedAt = membershipAssignDto.getCreatedAt();
    LocalDateTime membershipSubscriptionExpiredAt =
        membershipSubscriptionCreatedAt.withYear(membershipSubscriptionCreatedAt.getYear() + 1);
    Membership found = findMembershipByMembershipId(membershipAssignDto.getMembershipId());
    Membership membership =
        Membership.addMore(found, membershipSubscriptionCreatedAt, membershipSubscriptionExpiredAt);
    membershipRepository.save(membership);
  }

  public Membership findMembershipByMembershipId(Long membershipId) {
    Membership membership =
        membershipRepository
            .findByMembershipId(membershipId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_MEMBERSHIP));
    if(membership == null) {
      throw new NoMembershipException();
    }
    return membership;
  }
}
