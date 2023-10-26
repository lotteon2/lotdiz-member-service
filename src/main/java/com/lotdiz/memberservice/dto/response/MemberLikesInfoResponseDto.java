package com.lotdiz.memberservice.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberLikesInfoResponseDto {

    private Boolean isLikes;
    private Long likeCount;
}
