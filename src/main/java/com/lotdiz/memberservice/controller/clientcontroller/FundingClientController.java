package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.request.PoiintInfoForConsumptionRequestDto;
import com.lotdiz.memberservice.dto.request.PointInfoForRefundRequestDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FundingClientController {
  private final MemberService memberService;

  @PutMapping("/members/update-point")
  public ResponseEntity<ResultDataResponse> refundPoints(
      @RequestBody PointInfoForRefundRequestDto refundPoints) {
    memberService.refund(refundPoints);
    return ResponseEntity.ok()
        .body(
            ResultDataResponse.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(HttpStatus.OK.name())
                .detail("포인트 수정 성공")
                .build());
  }

  @PutMapping("/members/use-point")
  public ResponseEntity<ResultDataResponse> usePoints(
      @RequestBody PoiintInfoForConsumptionRequestDto pointConsumptionDto) {
    memberService.consume(pointConsumptionDto);
    return ResponseEntity.ok()
        .body(
            ResultDataResponse.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(HttpStatus.OK.name())
                .detail("포인트 수정 성공")
                .build());
  }
}
