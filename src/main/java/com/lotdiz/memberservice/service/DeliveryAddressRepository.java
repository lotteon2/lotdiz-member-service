package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

}
