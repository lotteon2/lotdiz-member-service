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
public class FundingDetailsForShowResponseDto {
    private Integer projectFundingAchievementRate;
    private Long totalAccumulatedFundingAmount;
}
