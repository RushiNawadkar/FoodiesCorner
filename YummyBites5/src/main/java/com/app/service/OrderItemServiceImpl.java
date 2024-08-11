package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.OrderItemDTO;
import com.app.entities.OrderItem;
import com.app.repository.OrderItemRepository;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	 @Override
	 public OrderItem getOrderItemById(Long id) {
	        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("OrderItem not found"));
	    return orderItem;
	 } 
	 
	@Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItem = orderItemRepository.save(orderItem);
        return orderItemDTO;
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found");
        }
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItem.setOrderItemId(id); 
        orderItem = orderItemRepository.save(orderItem);
        return orderItemDTO;
    }

    @Override
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("OrderItem not found");
        }
        orderItemRepository.deleteById(id);
    }

	
}
