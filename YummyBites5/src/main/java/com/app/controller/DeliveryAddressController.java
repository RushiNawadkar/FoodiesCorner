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

import com.app.dto.DeliveryAddressDTO;
import com.app.entities.DeliveryAddress;
import com.app.service.DeliveryAddressService;

@RestController
@RequestMapping("Delivery_Address")
public class DeliveryAddressController {

	@Autowired
	private DeliveryAddressService deliveryAddressService;
	
	@GetMapping("/{id}")
    public ResponseEntity<DeliveryAddress> getDeliveryAddressById(@PathVariable Long id) {
		DeliveryAddress deliveryAddressDTO = deliveryAddressService.getDeliveryAddressById(id);
        return ResponseEntity.ok(deliveryAddressDTO);
    }

	@PostMapping
	public ResponseEntity<DeliveryAddressDTO> createDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) {
	    if (deliveryAddressDTO.getUser() == null) {
	        return ResponseEntity.badRequest().body(null);  
	    }
	    DeliveryAddressDTO createdAddress = deliveryAddressService.createDeliveryAddress(deliveryAddressDTO);
	    return ResponseEntity.ok(createdAddress);
	}


    @PutMapping("/{id}")
    public ResponseEntity<DeliveryAddressDTO> updateDeliveryAddress(@PathVariable Long id, @RequestBody DeliveryAddressDTO deliveryAddressDTO) {
        DeliveryAddressDTO updatedAddress = deliveryAddressService.updateDeliveryAddress(id, deliveryAddressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryAddress(@PathVariable Long id) {
        deliveryAddressService.deleteDeliveryAddress(id);
        return ResponseEntity.noContent().build();
    }
}
