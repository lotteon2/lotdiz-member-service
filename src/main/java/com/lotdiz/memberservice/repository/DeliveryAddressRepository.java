package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.DeliveryAddress;
import com.lotdiz.memberservice.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

  List<DeliveryAddress> findByMember(Member member);

  Optional<DeliveryAddress> findByDeliveryAddressId(Long deliveryAddressId);
}
