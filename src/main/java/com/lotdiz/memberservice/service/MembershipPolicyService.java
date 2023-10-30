package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.MembershipInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.entity.MembershipPolicy;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MembershipPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipPolicyService {
  private final MembershipPolicyRepository membershipPolicyRepository;

  /**
   * 멤버십 세부 정책 조회
   *
   * @param membership
   * @param membershipPolicy
   * @return MembershipPolicyInfoForShowResponseDto
   */
  public MembershipInfoForShowResponseDto getMembershipPolicyInfo(Membership membership, MembershipPolicy membershipPolicy) {
    return CustomMapper.toMembershipInfoForShowResponseDto(membership, membershipPolicy);
  }
}
