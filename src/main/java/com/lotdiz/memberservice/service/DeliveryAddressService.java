package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForCreateRequestDto;
import com.lotdiz.memberservice.dto.response.DeliveryAddressInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.DeliveryAddress;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.MessageMapper;
import com.lotdiz.memberservice.utils.CustomErrorMessage;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
  private final DeliveryAddressRepository deliveryAddressRepository;

  /**
   * 배송지 생성
   *
   * @param memberId
   * @param deliveryAddressInfoDto
   */
  @Transactional
  public void createDeliveryAddress(
      Long memberId, DeliveryAddressInfoForCreateRequestDto deliveryAddressInfoDto) {
    DeliveryAddress deliveryAddress = DeliveryAddress.create(memberId, deliveryAddressInfoDto);
    deliveryAddressRepository.save(deliveryAddress);
  }

  /**
   * 배송지 목록 보여주기
   *
   * @param memberId
   * @return List<DeliveryAddressInfoForShowResponseDto>
   */
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

  /**
   * 배송지 수정
   *
   * @param deliveryAddressId
   * @param deliveryAddressInfoDto
   */
  @Transactional
  public void renewDeliveryAddress(
      Long deliveryAddressId, DeliveryAddressInfoForChangeRequestDto deliveryAddressInfoDto) {
    DeliveryAddress deliveryAddress =
        deliveryAddressRepository
            .findByDeliveryAddressId(deliveryAddressId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_DELIVERY_ADDRESS));
    DeliveryAddress.renew(deliveryAddress, deliveryAddressInfoDto);
  }

  /**
   * 배송지 삭제
   *
   * @param deliveryAddressId
   */
  @Transactional
  public void removeDeliveryAddress(Long deliveryAddressId) {
    DeliveryAddress deliveryAddress =
        deliveryAddressRepository
            .findByDeliveryAddressId(deliveryAddressId)
            .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_DELIVERY_ADDRESS));
    deliveryAddressRepository.delete(deliveryAddress);
  }
}
