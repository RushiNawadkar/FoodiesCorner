package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DeliveryPersonDTO;
import com.app.entities.DeliveryPerson;
import com.app.service.DeliveryPersonService;

@RestController
@RequestMapping("/deliveryPerson")
public class DeliveryPersonController {

	@Autowired
	private DeliveryPersonService deliveryPersonService;
	
	 @GetMapping("/{id}")
	    public DeliveryPerson getDeliveryPersonById(@PathVariable Long id) {
	        return deliveryPersonService.getDeliveryPersonById(id);
	    }

	    @PostMapping("/add")
	    public DeliveryPersonDTO createDeliveryPerson(@RequestBody DeliveryPersonDTO deliveryPersonDTO) {
	        return deliveryPersonService.createDeliveryPerson(deliveryPersonDTO);
	    }

	    @PutMapping("/update/{id}")
	    public DeliveryPersonDTO updateDeliveryPerson(@PathVariable Long id, @RequestBody DeliveryPersonDTO deliveryPersonDTO) {
	        return deliveryPersonService.updateDeliveryPerson(id, deliveryPersonDTO);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteDeliveryPerson(@PathVariable Long id) {
	        deliveryPersonService.deleteDeliveryPerson(id);
	    }
}
