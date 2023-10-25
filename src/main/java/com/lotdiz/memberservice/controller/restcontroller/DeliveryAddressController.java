package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.request.DeliveryAddressInfoForCreateRequestDto;
import com.lotdiz.memberservice.dto.response.DeliveryAddressInfoForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.DeliveryAddressService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeliveryAddressController {
  private final DeliveryAddressService deliveryAddressService;

  @PostMapping("/members/delivery-address")
  public ResponseEntity<ResultDataResponse<Object>> addDeliveryAddress(
      @RequestHeader Long memberId,
      @Valid @RequestBody DeliveryAddressInfoForCreateRequestDto deliveryAddressInfoDto) {
    deliveryAddressService.createDeliveryAddress(memberId, deliveryAddressInfoDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.CREATED.value()),
                HttpStatus.CREATED.name(),
                "배송지 생성 성공",
                null));
  }

  @GetMapping("/members/delivery-address")
  public ResponseEntity<ResultDataResponse<List<DeliveryAddressInfoForShowResponseDto>>>
      inquireDeliveryAddress(@RequestHeader Long memberId) {
    List<DeliveryAddressInfoForShowResponseDto> deliveryAddressInfoDtos =
        deliveryAddressService.inquireDeliveryAddresses(memberId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "배송지 목록 조회 성공",
                deliveryAddressInfoDtos));
  }

  @PutMapping("/members/delivery-address/{deliveryAddressId}")
  public ResponseEntity<ResultDataResponse<Object>> renewDeliveryAddress(
      @PathVariable("deliveryAddressId") Long deliveryAddressId,
      @Valid @RequestBody DeliveryAddressInfoForChangeRequestDto deliveryAddressInfoDto) {
    deliveryAddressService.renewDeliveryAddress(deliveryAddressId, deliveryAddressInfoDto);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "배송지 수정 성공", null));
  }

  @DeleteMapping("/members/delivery-address/{deliveryAddressId}")
  public ResponseEntity<ResultDataResponse<Object>> removeDeliveryAddress(
      @PathVariable("deliveryAddressId") Long deliveryAddressId) {
    deliveryAddressService.removeDeliveryAddress(deliveryAddressId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "배송지 삭제 성공", null));
  }
}
