package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinRequestDto;
import com.lotdiz.memberservice.dto.response.MembershipInfoForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import com.lotdiz.memberservice.service.MembershipPolicyService;
import com.lotdiz.memberservice.service.MembershipService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<ResultDataResponse<MembershipInfoForShowResponseDto>> showMembership(
      @RequestHeader Long memberId) {
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "멤버십 조회 성공",
                membershipService.getMembershipInfo(memberId)));
  }

  @PostMapping("/members/membership")
  public ResponseEntity<ResultDataResponse<String>> joinMembership(
      @RequestHeader Long memberId,
      @Valid @RequestBody MembershipInfoForJoinRequestDto membershipJoinDto) {
    String nextFedirectPcUrl = membershipService.createMembership(memberId, membershipJoinDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "카카오페이 QR코드 요청 성공",
                nextFedirectPcUrl));
  }

  @DeleteMapping("/members/membership")
  public ResponseEntity<ResultDataResponse<Object>> removeMembership(@RequestHeader Long memberId) {
    memberService.breakMembership(memberId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.toString(),
                "멤버십 해제 성공",
                null));
  }
}
