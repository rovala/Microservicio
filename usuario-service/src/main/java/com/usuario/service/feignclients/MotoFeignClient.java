package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.models.Moto;

@FeignClient(name="moto-service",path="/moto")
public interface MotoFeignClient
{
	@PostMapping()
	public Moto save(@RequestBody Moto moto);
	
	@GetMapping("/usuario/{idUser}")
	public List<Moto> getMotos(@PathVariable Long idUser);

}
