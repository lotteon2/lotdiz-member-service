package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.entity.DeliveryAddress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

  List<DeliveryAddress> findByMemberId(Long memberId);

  Optional<DeliveryAddress> findByDeliveryAddressId(Long deliveryAddressId);
}
