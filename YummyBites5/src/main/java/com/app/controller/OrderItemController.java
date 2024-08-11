package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OrderItemDTO;
import com.app.entities.OrderItem;
import com.app.service.OrderItemService;

@RestController
@RequestMapping("/orderitem")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	
	 @GetMapping("/{id}")
	    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
		 OrderItem orderItemDTO = orderItemService.getOrderItemById(id);
	        return ResponseEntity.ok(orderItemDTO);
	    }

	    @PostMapping("/add")
	    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
	        OrderItemDTO createdOrderItem = orderItemService.createOrderItem(orderItemDTO);
	        return ResponseEntity.ok(createdOrderItem);
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
	        OrderItemDTO updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDTO);
	        return ResponseEntity.ok(updatedOrderItem);
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
	        orderItemService.deleteOrderItem(id);
	        return ResponseEntity.noContent().build();
	    }
}
