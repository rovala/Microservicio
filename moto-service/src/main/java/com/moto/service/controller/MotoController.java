package com.moto.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moto.service.entity.Moto;
import com.moto.service.service.MotoService;

@RestController
@RequestMapping("/moto")
public class MotoController
{
	@Autowired
	private MotoService motoService;
	
	@GetMapping
	public ResponseEntity<List<Moto>> listarMotos()
	{
		List<Moto> listaMotos=motoService.getAll();
		if (listaMotos.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaMotos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Moto> getMotoById(@PathVariable Long id)
	{
		Moto moto=motoService.getMotoById(id);
		if (moto==null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(moto);
	}
	
	@PostMapping
	public ResponseEntity<Moto> saveMoto(@RequestBody Moto moto)
	{
		Moto newMoto=motoService.saveMoto(moto);
		return ResponseEntity.ok(newMoto);
	}

	@GetMapping("/usuario/{idUser}")
	public ResponseEntity<List<Moto>> listarMotosByUsuario(@PathVariable Long idUser)
	{
		List<Moto> listaMotos=motoService.getAllByIdUser(idUser);
		if (listaMotos.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaMotos);
	}
}
