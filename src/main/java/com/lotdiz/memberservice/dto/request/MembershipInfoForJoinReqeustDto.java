package com.lotdiz.memberservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipInfoForJoinReqeustDto {
    @NotNull
    private Long memberId;

    @NotNull
    private Long membershipPolicyId;

    @NotNull
    private String itemName;

    @NotNull
    private String totalAmount;

    @NotNull
    private String taxFreeAmount;
}
