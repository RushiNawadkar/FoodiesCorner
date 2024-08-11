package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
