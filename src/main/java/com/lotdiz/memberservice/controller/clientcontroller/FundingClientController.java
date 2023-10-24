package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.request.PointInfoForConsumptionRequestDto;
import com.lotdiz.memberservice.dto.request.PointInfoForRefundRequestDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingClientController {
  private final MemberService memberService;

  @PutMapping("/members/point/refund")
  public ResultDataResponse<Object> refundPoints(
      @RequestBody PointInfoForRefundRequestDto pointRefundDto) {
    memberService.refundPoints(pointRefundDto);
    return new ResultDataResponse<>(
        String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "포인트 환불 성공", null);
  }

  @PutMapping("/members/point")
  public ResultDataResponse<Object> usePoints(
      @Valid @RequestBody PointInfoForConsumptionRequestDto pointConsumptionDto) {
    memberService.consumePoints(pointConsumptionDto);
    return new ResultDataResponse<>(
        String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "포인트 수정 성공", null);
  }
}
