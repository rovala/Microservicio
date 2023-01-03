package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.models.Bicycle;

@FeignClient(name="bicicleta-service",path="/bicicleta")
public interface BicycleFeignClient
{
	@PostMapping
	Bicycle Save(@RequestBody Bicycle bicycle);
	
	@GetMapping("/usuario/{idUser}")
	List<Bicycle> getBicycles(@PathVariable Long idUser);
}
