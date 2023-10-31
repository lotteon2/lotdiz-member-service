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
public class ProjectDetailsForShowResponseDto {
    private Long projectId;
    private Integer remainingDays; //펀딩 남은 기간
    private String projectName;
    private String projectThumbnailImageUrl;
    private String makerName;
}
