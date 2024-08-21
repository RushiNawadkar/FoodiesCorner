package com.app.service;

import com.app.dto.OrderDTO;
import com.app.entities.Order;

public interface OrderService {

	Order getOrderById(Long id);

	OrderDTO createOrder(OrderDTO orderDTO);

	OrderDTO updateOrder(Long id, OrderDTO orderDTO);

	void deleteOrder(Long id);

}
