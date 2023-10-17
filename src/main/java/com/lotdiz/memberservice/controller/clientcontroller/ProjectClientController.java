package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.request.PointInfoForRefundRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForProjectResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectClientController {
  private final MemberService memberService;

  @GetMapping("/members")
  public ResultDataResponse<Map<String, MemberInfoForProjectResponseDto>> inquireMemberInfos(
      @RequestParam List<Long> memberIds) {
    Map<String, MemberInfoForProjectResponseDto> memberInfos =
        memberService.inquireNameAndProfileImage(memberIds);
    return new ResultDataResponse<>("200", HttpStatus.OK.name(), "성공", memberInfos);
  }

  @PostMapping("/members/update-point")
  public ResponseEntity<ResultDataResponse> refundPoint(
      @RequestBody List<PointInfoForRefundRequestDto> refundPoints) {
    memberService.refund(refundPoints);
    return ResponseEntity.ok().body(
        ResultDataResponse.builder()
            .code(String.valueOf(HttpStatus.OK.value()))
            .message(HttpStatus.OK.name())
            .detail("포인트 수정 성공")
            .build()
    );
  }
}
