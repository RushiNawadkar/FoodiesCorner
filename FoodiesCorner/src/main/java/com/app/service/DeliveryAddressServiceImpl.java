package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.DeliveryAddressDTO;
import com.app.entities.DeliveryAddress;
import com.app.entities.User;
import com.app.repository.DeliveryAddressRepository;
import com.app.repository.UserRepository;


@Service
@Transactional
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;
	
	@Autowired 
	private UserRepository userrepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
    public DeliveryAddress getDeliveryAddressById(Long id) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return deliveryAddress;
    }

    @Override
    public DeliveryAddressDTO createDeliveryAddress(DeliveryAddressDTO addressDto) {
    	User user =  userrepository.findById(addressDto.getUser())
    			.orElseThrow(()->new RuntimeException());
    	
        DeliveryAddress address = modelMapper.map(addressDto, DeliveryAddress.class);
        address.setUser(user);
        address = deliveryAddressRepository.save(address);
        return addressDto;
    }

    @Override
    public DeliveryAddressDTO updateDeliveryAddress(Long id, DeliveryAddressDTO deliveryAddressDTO) {
        DeliveryAddress existingAddress = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Copy properties from DTO to the existing entity
        modelMapper.map(deliveryAddressDTO, existingAddress);

        DeliveryAddress updatedAddress = deliveryAddressRepository.save(existingAddress);
        return deliveryAddressDTO;
    }

    @Override
    public void deleteDeliveryAddress(Long id) {
        DeliveryAddress existingAddress = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        deliveryAddressRepository.delete(existingAddress);
    }
	
}
