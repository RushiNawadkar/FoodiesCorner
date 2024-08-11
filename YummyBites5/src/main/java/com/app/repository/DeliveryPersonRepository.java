package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.DeliveryPerson;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

}
