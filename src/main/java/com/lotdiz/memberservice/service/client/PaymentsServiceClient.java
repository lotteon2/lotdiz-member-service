package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.KakaoPayReadyForMemberResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${endpoint.payment-service}")
public interface PaymentsServiceClient {

  @PostMapping("/api/membership/payments/ready")
  ResponseEntity<ResultDataResponse<KakaoPayReadyForMemberResponseDto>> getMembershipSubscriptionId(@Valid @RequestBody PaymentsInfoForKakaoPayRequestDto paymentsDto);
}
