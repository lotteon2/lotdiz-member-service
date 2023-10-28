package com.lotdiz.memberservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressInfoForChangeRequestDto {
    private String deliveryAddressRecipientName;
    private String deliveryAddressRecipientPhoneNumber;
    private String deliveryAddressRecipientEmail;
    private String deliveryAddressRequest;
    private String deliveryAddressRoadName;
    private String deliveryAddressDetail;
    private String deliveryAddressZipCode;
    private Boolean deliveryAddressIsDefault;
}
