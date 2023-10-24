package com.lotdiz.memberservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoForQueryResponseDto {
  private Long membershipPolicyId;
  private Long memberId;
  private String memberName;
  private String memberPhoneNumber;
  private String memberProfileImageUrl;
}
