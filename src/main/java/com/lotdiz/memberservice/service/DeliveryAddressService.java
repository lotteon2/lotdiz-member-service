package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForCreateRequestDto;
import com.lotdiz.memberservice.entity.DeliveryAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
  private final DeliveryAddressRepository deliveryAddressRepository;

  public void createDeliveryAddress(
      Long memberId, DeliveryAddressInfoForCreateRequestDto deliveryAddressInfoDto) {
    DeliveryAddress deliveryAddress = DeliveryAddress.create(memberId, deliveryAddressInfoDto);
    deliveryAddressRepository.save(deliveryAddress);
  }
}
