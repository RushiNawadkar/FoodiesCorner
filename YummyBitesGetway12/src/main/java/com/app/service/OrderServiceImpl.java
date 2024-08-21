package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ReaserchNotFound;
import com.app.dto.OrderDTO;
import com.app.entities.DeliveryAddress;
import com.app.entities.Order;
import com.app.entities.Restaurant;
import com.app.entities.User;
import com.app.repository.DeliveryAddressRepository;
import com.app.repository.OrderRepository;
import com.app.repository.RestaurentRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;
	
	@Autowired
	private RestaurentRepository restaurentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public Order getOrderById(Long id) {
		
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return order;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
    	DeliveryAddress address=deliveryAddressRepository.
				findById(orderDTO.getDeliveryAddressId()).orElseThrow(()->new ReaserchNotFound("InvalidId!!!!"));
        Restaurant restaurant=restaurentRepository.
        		findById(orderDTO.getRestaurantId()).orElseThrow(()->new ReaserchNotFound("InvalidId!!!")); 
    	User user= userRepository.findById(orderDTO.getUserId()).orElseThrow(()->new ReaserchNotFound("InvalidId!!!!")); 

        Order order = modelMapper.map(orderDTO, Order.class);
    	order.setUser(user);
        order.setDeliveryAddress(address);
        order.setRestaurant(restaurant);
        Order savedOrder = orderRepository.save(order);
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        modelMapper.map(orderDTO, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return orderDTO;
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
