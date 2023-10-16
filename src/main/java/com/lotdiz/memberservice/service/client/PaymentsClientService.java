package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.ResultDataResponse;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakoaPayRequestDto;
import com.lotdiz.memberservice.service.client.PaymentsServiceClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsClientService {
  private final PaymentsServiceClient paymentsServiceClient;

  public Long getMembershipSubscription(PaymentsInfoForKakoaPayRequestDto paymentsDto) {
    return paymentsServiceClient.getMembershipSubscriptionId(paymentsDto).getData().get("membershipSubscriptionId");
  }
}
