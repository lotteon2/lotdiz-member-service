package com.lotdiz.memberservice.service.client;

import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.dto.response.KakaoPayReadyForMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsClientService {
  private final PaymentsServiceClient paymentsServiceClient;

  public KakaoPayReadyForMemberResponseDto getMembershipSubscription(PaymentsInfoForKakaoPayRequestDto paymentsDto) {
    return paymentsServiceClient.getMembershipSubscriptionId(paymentsDto).getBody().getData();
  }
}
