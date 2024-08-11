package com.app.service;

import com.app.dto.OrderItemDTO;
import com.app.entities.OrderItem;

public interface OrderItemService {

	OrderItem getOrderItemById(Long id);

	OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

	OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);

	void deleteOrderItem(Long id);

}
