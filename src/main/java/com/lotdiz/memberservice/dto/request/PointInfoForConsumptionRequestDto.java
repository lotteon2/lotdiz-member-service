package com.lotdiz.memberservice.dto.request;

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
public class PointInfoForConsumptionRequestDto {
    private Long memberId;
    private Long memberPoint;
}
