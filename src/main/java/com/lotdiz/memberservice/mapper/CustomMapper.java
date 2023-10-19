package com.lotdiz.memberservice.mapper;

import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import com.lotdiz.memberservice.entity.Member;

public class CustomMapper {
  public static MemberInfoForQueryResponseDto MemberInfoForQueryResponseDtoMapper(Member member) {
    return MemberInfoForQueryResponseDto.builder()
        .memberId(member.getMemberId())
        .memberName(member.getMemberName())
        .memberPhoneNumber(member.getMemberPhoneNumber())
        .memberProfileImageUrl(member.getMemberProfileImageUrl())
        .build();
  }

  public static PaymentsInfoForKakaoPayRequestDto PaymentsInfoForKakoaPayRequestDtoMapper(
      Long membershipId, MembershipInfoForJoinReqeustDto memberJoinDto) {
    return PaymentsInfoForKakaoPayRequestDto.builder()
        .itemName(memberJoinDto.getItemName())
        .quantity("1")
        .totalAmount(memberJoinDto.getTotalAmount())
        .taxFreeAmount(memberJoinDto.getTaxFreeAmount())
        .membershipId(String.valueOf(membershipId))
        .build();
  }

    public static LikesDetailsForShowResponseDto toLikesDetailsForShowResponseDto(ProjectDetailsForShowResponseDto projectDetailsDto, FundingDetailsForShowResponseDto fundingDetailsDto) {
      return LikesDetailsForShowResponseDto.builder()
          .remainingProjectPeriod(projectDetailsDto.getRemainingProjectPeriod())
          .projectName(projectDetailsDto.getProjectName())
          .projectThumbnailImage(projectDetailsDto.getProjectThumbnailImage())
          .makerName(projectDetailsDto.getMakerName())
          .projectFundingAchievementRate(fundingDetailsDto.getProjectFundingAchievementRate())
          .totalAccumulatedFundingAmount(fundingDetailsDto.getTotalAccumulatedFundingAmount())
          .build();
    }
}
