package com.carro.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.entity.Car;
import com.carro.service.service.CarService;

@RestController
@RequestMapping("/carro")
public class CarController
{
	@Autowired
	CarService carService;
	
	@GetMapping
	public ResponseEntity<List<Car>> listarCarros()
	{
		List<Car> listaCarros=carService.getAll();
		if (listaCarros.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaCarros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarroById(@PathVariable Long id)
	{
		Car car=carService.getCarById(id);
		if (car==null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);
	}
	
	@PostMapping
	public ResponseEntity<Car> saveCarro(@RequestBody Car car)
	{
		Car newCar=carService.saveCar(car);
		return ResponseEntity.ok(newCar);
	}
	
	@GetMapping("/usuario/{idUser}")
	public ResponseEntity<List<Car>> listaCarroByUsuario(@PathVariable Long idUser)
	{
		List<Car> listaCarros=carService.getAllByIdUser(idUser);
		if (listaCarros.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaCarros);
	}
}
