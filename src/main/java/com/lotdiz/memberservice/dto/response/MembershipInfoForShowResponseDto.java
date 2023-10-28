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
public class MembershipInfoForShowResponseDto {
    private String membershipPolicyGrade;
    private Long membershipPolicySubscriptionFee;
    private Integer membershipPolicyDiscountRate;
    private Integer membershipPolicyPointAccumulationRate;
    private String membershipSubscriptionCreatedAt;
    private String membershipSubscriptionExpiredAt;
}
