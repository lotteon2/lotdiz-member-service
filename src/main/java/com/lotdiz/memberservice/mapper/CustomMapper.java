package com.lotdiz.memberservice.mapper;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import org.springframework.stereotype.Component;

public class CustomMapper {
    public static MemberInfoForQueryResponseDto MemberInfoForQueryResponseDtoMapper(Member member) {
        return MemberInfoForQueryResponseDto.builder()
            .memberId(member.getMemberId())
            .memberName(member.getMemberName())
            .memberPhoneNumber(member.getMemberPhoneNumber())
            .memberProfileImageUrl(member.getMemberProfileImageUrl())
            .build();
    }
}
