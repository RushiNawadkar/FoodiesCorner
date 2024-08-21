package com.app.service;

import com.app.dto.DeliveryAddressDTO;
import com.app.entities.DeliveryAddress;

public interface DeliveryAddressService {

	DeliveryAddress getDeliveryAddressById(Long id);

	DeliveryAddressDTO createDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO);

	DeliveryAddressDTO updateDeliveryAddress(Long id, DeliveryAddressDTO deliveryAddressDTO);

	void deleteDeliveryAddress(Long id);

}
