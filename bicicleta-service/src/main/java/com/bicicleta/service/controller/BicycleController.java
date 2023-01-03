package com.bicicleta.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bicicleta.service.entity.Bicycle;
import com.bicicleta.service.service.BicycleService;

@RestController
@RequestMapping("/bicicleta")
public class BicycleController
{
	@Autowired
	private BicycleService bicycleService;
	
	@GetMapping
	public ResponseEntity<List<Bicycle>> listarBicicletas()
	{
		List<Bicycle> listaBicicletas=bicycleService.getAll();
		if (listaBicicletas.isEmpty()||listaBicicletas==null)
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaBicicletas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bicycle> getCarroById(@PathVariable Long id)
	{
		Bicycle bicycle=bicycleService.getBicycle(id);
		if (bicycle==null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bicycle); 
	}
	
	@PostMapping
	public ResponseEntity<Bicycle> saveBicicleta(@RequestBody Bicycle bicycle)
	{
		Bicycle newBicycle=bicycleService.saveBicycle(bicycle);
		return ResponseEntity.ok(newBicycle);
	}
	
	@GetMapping("/usuario/{idUser}")
	public ResponseEntity<List<Bicycle>> listaBicicletaByUsuario(@PathVariable Long idUser)
	{
		List<Bicycle> listaBicicletas=bicycleService.getAllByIdUser(idUser);
		if (listaBicicletas.isEmpty()||listaBicicletas==null)
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaBicicletas);
	}
}
