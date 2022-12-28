package com.carro.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carro.service.entity.Car;
import com.carro.service.repository.CarRepository;

@Service
public class CarService
{
	@Autowired
	private CarRepository carRepository;
	
	public List<Car> getAll()
	{
		return carRepository.findAll();
	}
	
	public Car getCarById(Long id)
	{
		return carRepository.findById(id).orElse(null);
	}
	
	public Car saveCar(Car car)
	{
		return carRepository.save(car);
	}
	
	public List<Car> getAllByIdUser(Long idUser)
	{
		return carRepository.findByIdUser(idUser);
	}
}
