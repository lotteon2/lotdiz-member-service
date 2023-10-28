package com.lotdiz.memberservice.dto.request;

import javax.validation.constraints.Email;
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
public class DeliveryAddressInfoForCreateRequestDto {
    private String deliveryAddressRecipientName; //배송 수신자 이름
    private String deliveryAddressRecipientPhoneNumber; //배송 수신자 전화번호
    @Email
    private String deliveryAddressRecipientEmail; //배송 수신자 이메일
    private String deliveryAddressRequest; //배송 요청사항
    private String deliveryAddressRoadName; //도로명 주소
    private String deliveryAddressDetail; //상세주소
    private String deliveryAddressZipCode; //우편번호
    private Boolean deliveryAddressIsDefault;
}
