package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.request.PointInfoForConsumptionRequestDto;
import com.lotdiz.memberservice.dto.request.PointInfoForRefundRequestDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingClientController {
  private final MemberService memberService;

  @PutMapping("/members/point/refund")
  public ResponseEntity<ResultDataResponse<Object>> refundPoints(
      @RequestBody PointInfoForRefundRequestDto pointRefundDto) {
    memberService.refundPoints(pointRefundDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "포인트 환불 성공", null));
  }

  @PutMapping("/members/point")
  public ResponseEntity<ResultDataResponse<Object>> usePoints(
      @Valid @RequestBody PointInfoForConsumptionRequestDto pointConsumptionDto) {
    memberService.consumePoints(pointConsumptionDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "포인트 수정 성공", null));
  }
}
