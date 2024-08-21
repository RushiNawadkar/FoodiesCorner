package com.app.service;

import com.app.dto.DeliveryPersonDTO;
import com.app.entities.DeliveryPerson;

public interface DeliveryPersonService {

	DeliveryPerson getDeliveryPersonById(Long id);

	DeliveryPersonDTO createDeliveryPerson(DeliveryPersonDTO deliveryPersonDTO);

	DeliveryPersonDTO updateDeliveryPerson(Long id, DeliveryPersonDTO deliveryPersonDTO);

	void deleteDeliveryPerson(Long id);

}
