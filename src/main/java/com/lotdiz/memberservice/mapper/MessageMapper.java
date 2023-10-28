package com.lotdiz.memberservice.mapper;

import com.lotdiz.memberservice.dto.response.DeliveryAddressInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.DeliveryAddress;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface MessageMapper {
  MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

//  MembershipPolicyInfoForShowResponseDto toMembershipPolicyInfoDto(MembershipPolicy membershipPolicy);
  DeliveryAddressInfoForShowResponseDto toDeliveryAddressInfoDto(DeliveryAddress deliveryAddress);
}
