package com.lotdiz.memberservice.mapper;

import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinRequestDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.dto.response.MembershipInfoForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.entity.MembershipPolicy;

public class CustomMapper {
  public static MemberInfoForQueryResponseDto MemberInfoForQueryResponseDtoMapper(Member member) {
    return MemberInfoForQueryResponseDto.builder()
        .memberId(member.getMemberId())
        .memberName(member.getMemberName())
        .memberPhoneNumber(member.getMemberPhoneNumber())
        .memberProfileImageUrl(member.getMemberProfileImageUrl())
        .memberEmail(member.getMemberEmail())
        .createdAt(member.getCreatedAt().toString())
        .build();
  }

  public static PaymentsInfoForKakaoPayRequestDto PaymentsInfoForKakoaPayRequestDtoMapper(
      Long membershipId, MembershipInfoForJoinRequestDto memberJoinDto) {
    return PaymentsInfoForKakaoPayRequestDto.builder()
        .itemName(memberJoinDto.getItemName())
        .quantity("1")
        .totalAmount(memberJoinDto.getTotalAmount())
        .taxFreeAmount(memberJoinDto.getTaxFreeAmount())
        .membershipId(String.valueOf(membershipId))
        .build();
  }

  public static LikesDetailsForShowResponseDto toLikesDetailsForShowResponseDto(
      ProjectDetailsForShowResponseDto projectDetailsDto,
      FundingDetailsForShowResponseDto fundingDetailsDto) {
    return LikesDetailsForShowResponseDto.builder()
        .projectId(projectDetailsDto.getProjectId())
        .remainingDays(projectDetailsDto.getRemainingDays())
        .projectName(projectDetailsDto.getProjectName())
        .projectThumbnailImageUrl(projectDetailsDto.getProjectThumbnailImageUrl())
        .makerName(projectDetailsDto.getMakerName())
        .fundingAchievementRate(fundingDetailsDto.getFundingAchievementRate())
        .accumulatedFundingAmount(fundingDetailsDto.getAccumulatedFundingAmount())
        .build();
  }

  public static MembershipInfoForShowResponseDto toMembershipInfoForShowResponseDto(
      Membership membership, MembershipPolicy membershipPolicy) {
    return MembershipInfoForShowResponseDto.builder()
        .membershipPolicyGrade(membershipPolicy.getMembershipPolicyGrade())
        .membershipPolicySubscriptionFee(membershipPolicy.getMembershipPolicySubscriptionFee())
        .membershipPolicyDiscountRate(membershipPolicy.getMembershipPolicyDiscountRate())
        .membershipPolicyPointAccumulationRate(
            membershipPolicy.getMembershipPolicyPointAccumulationRate())
        .membershipSubscriptionCreatedAt(membership.getMembershipSubscriptionCreatedAt().toString())
        .membershipSubscriptionExpiredAt(membership.getMembershipSubscriptionExpiredAt().toString())
        .build();
  }
}
