package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForCreateRequestDto;
import com.lotdiz.memberservice.dto.response.DeliveryAddressInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.DeliveryAddress;
import com.lotdiz.memberservice.mapper.MessageMapper;
import java.util.ArrayList;
import java.util.List;
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

  public List<DeliveryAddressInfoForShowResponseDto> inquireDeliveryAddresses(Long memberId) {
    List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findByMemberId(memberId);
    List<DeliveryAddressInfoForShowResponseDto> deliveryAddressDtos = new ArrayList<>();
    for (DeliveryAddress deliveryAddress : deliveryAddresses) {
      DeliveryAddressInfoForShowResponseDto deliveryAddressInfoDto =
          MessageMapper.INSTANCE.toDeliveryAddressInfoDto(deliveryAddress);
      deliveryAddressDtos.add(deliveryAddressInfoDto);
    }
    return deliveryAddressDtos;
  }
}
