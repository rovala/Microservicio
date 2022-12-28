package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.models.Car;

@FeignClient(name="carro-service",url="http://localhost:8002",path = "/carro")
public interface CarFeignClient
{
	@PostMapping()
	public Car save(@RequestBody Car car);
	
	@GetMapping("/usuario/{idUser}")
	public List<Car> getCars(@PathVariable Long idUser);
	
}
