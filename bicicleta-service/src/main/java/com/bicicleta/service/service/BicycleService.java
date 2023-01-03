package com.bicicleta.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bicicleta.service.entity.Bicycle;
import com.bicicleta.service.repository.BicycleRepository;

@Service
public class BicycleService
{
	@Autowired
	private BicycleRepository bicycleRepository;
	
	public List<Bicycle> getAll()
	{
		return bicycleRepository.findAll();
	}
	
	public Bicycle getBicycle(Long id)
	{
		return bicycleRepository.findById(id).orElse(null);
	}
	
	public Bicycle saveBicycle(Bicycle bicycle)
	{
		return bicycleRepository.save(bicycle);
	}
	
	public List<Bicycle> getAllByIdUser(Long idUser)
	{
		return bicycleRepository.findByIdUser(idUser);
	}
}
