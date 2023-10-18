package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.service.MembershipService;
import com.lotdiz.memberservice.service.client.PaymentsClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentsClientController {
  private final PaymentsClientService paymentsClientService;
  private final MembershipService membershipService;

  @GetMapping("/api/payments/client/test")
  private ResultDataResponse<Long> getMembershipSubscriptionIdForMembershipJoin() {
    PaymentsInfoForKakaoPayRequestDto paymentsDto =
        PaymentsInfoForKakaoPayRequestDto.builder()
            .itemName("펀딩프렌즈")
            .quantity("1")
            .totalAmount("6900")
            .taxFreeAmount("0")
            .membershipId("1")
            .build();

    return new ResultDataResponse<>(
        "200",
        HttpStatus.OK.name(),
        "멤버십 구독 정보 요청 성공",
        paymentsClientService.getMembershipSubscription(paymentsDto));
  }
}
