package com.lotdiz.memberservice.entity;

import com.lotdiz.memberservice.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long deliveryAddressId;

    @Column(nullable = false)
    private String deliveryAddressRecipientName;

    @Column(nullable = false)
    private String deliveryAddressRecipientPhoneNumber;

    private String deliveryAddressRecipientEmail;

    private String deliveryAddressRequest;

    @Column(nullable = false)
    private String deliveryAddressRoadName;

    @Column(nullable = false)
    private String deliveryAddressDetail;

    @Column(nullable = false)
    private String deliveryAddressZipCode;

    @Column(nullable = false)
    @Builder.Default
    private Boolean deliveryAddressIsDefault = false;

    @Column(nullable = false)
    private Long memberId;
}
