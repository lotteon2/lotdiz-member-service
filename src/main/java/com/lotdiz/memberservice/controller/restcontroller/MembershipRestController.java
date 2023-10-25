package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinRequestDto;
import com.lotdiz.memberservice.dto.response.MembershipPolicyInfoForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.service.MemberService;
import com.lotdiz.memberservice.service.MembershipPolicyService;
import com.lotdiz.memberservice.service.MembershipService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipRestController {
  private final MembershipService membershipService;
  private final MemberService memberService;
  private final MembershipPolicyService membershipPolicyService;

  @GetMapping("/members/membership")
  public ResponseEntity<ResultDataResponse<Object>> showMembership(@RequestHeader Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);
    Membership membership = member.getMembership();
    MembershipPolicyInfoForShowResponseDto membershipPolicyDto =
        membershipPolicyService.getMembershipPolicyInfo(membership.getMembershipPolicyId());
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "멤버십 조회 성공",
                membershipPolicyDto));
  }

  @PostMapping("/members/membership")
  public ResponseEntity<ResultDataResponse<String>> joinMembership(
      @RequestHeader Long memberId,
      @Valid @RequestBody MembershipInfoForJoinRequestDto membershipJoinDto) {
    String next_redirect_pc_url = membershipService.createMembership(memberId, membershipJoinDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "카카오페이 QR코드 요청 성공",
                next_redirect_pc_url));
  }

  @DeleteMapping("/members/membership/{membershipId}")
  public ResponseEntity<ResultDataResponse<Object>> removeMembership(
      @RequestHeader Long memberId, @PathVariable("membershipId") Long membershipId) {
    memberService.breakMembership(memberId, membershipId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.toString(),
                "멤버십 해제 성공",
                null));
  }
}
