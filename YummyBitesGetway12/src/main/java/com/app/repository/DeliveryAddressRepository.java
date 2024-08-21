package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.DeliveryAddress;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

}
