package com.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.DeliveryPersonDTO;
import com.app.entities.DeliveryPerson;
import com.app.repository.DeliveryPersonRepository;

@Service
@Transactional
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

	@Autowired
	private DeliveryPersonRepository deliveryPersonRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
    public DeliveryPerson getDeliveryPersonById(Long id) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery Person not found"));
        return deliveryPerson;
    }

    @Override
    public DeliveryPersonDTO createDeliveryPerson(DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPerson deliveryPerson = modelMapper.map(deliveryPersonDTO, DeliveryPerson.class);
        deliveryPerson = deliveryPersonRepository.save(deliveryPerson);
        return modelMapper.map(deliveryPerson, DeliveryPersonDTO.class);
    }

    @Override
    public DeliveryPersonDTO updateDeliveryPerson(Long id, DeliveryPersonDTO deliveryPersonDTO) {
        DeliveryPerson existingDeliveryPerson = deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery Person not found"));

        modelMapper.map(deliveryPersonDTO, existingDeliveryPerson);
        existingDeliveryPerson = deliveryPersonRepository.save(existingDeliveryPerson);
        return modelMapper.map(existingDeliveryPerson, DeliveryPersonDTO.class);
    }

    @Override
    public void deleteDeliveryPerson(Long id) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery Person not found"));
        deliveryPersonRepository.delete(deliveryPerson);
    }
}
