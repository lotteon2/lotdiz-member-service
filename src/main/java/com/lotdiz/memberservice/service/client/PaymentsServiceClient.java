package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8085")
public interface PaymentsServiceClient {

  @PostMapping("/api/membership/payments/ready")
  ResultDataResponse<Map<String, Long>> getMembershipSubscriptionId(
      @RequestBody PaymentsInfoForKakaoPayRequestDto paymentsDto);
}
