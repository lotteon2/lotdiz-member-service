package com.lotdiz.memberservice.mapper;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakoaPayRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import org.jetbrains.annotations.NotNull;
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

    public static PaymentsInfoForKakoaPayRequestDto PaymentsInfoForKakoaPayRequestDtoMapper(Long membershipId,
        MembershipInfoForJoinReqeustDto memberJoinDto) {
        return PaymentsInfoForKakoaPayRequestDto.builder()
            .itemName(memberJoinDto.getItemName())
            .quantity("1")
            .totalAmount(memberJoinDto.getTotalAmount())
            .taxFreeAmount(memberJoinDto.getTaxFreeAmount())
            .membershipId(String.valueOf(membershipId))
            .build();
    }
}
