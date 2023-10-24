package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.MembershipPolicyInfoForShowResponseDto;
import com.lotdiz.memberservice.mapper.MessageMapper;
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
   * @param membershipPolicyId
   * @return MembershipPolicyInfoForShowResponseDto
   */
  public MembershipPolicyInfoForShowResponseDto inquireMembershipPolicyInfo(
      Long membershipPolicyId) {
    return MessageMapper.INSTANCE.toMembershipPolicyInfoDto(
        membershipPolicyRepository.findByMembershipPolicyId(membershipPolicyId));
  }
}
