package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.User;
import com.usuario.service.models.Car;
import com.usuario.service.models.Moto;
import com.usuario.service.service.UserService;

@RestController
@RequestMapping("/usuario")
public class UserController
{
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> listarUsuarios()
	{
		List<User> listUser=userService.getAll();
		if (listUser.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listUser);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> encontrarUsuario(@PathVariable Long id)
	{
		User foundUser=userService.getUserById(id);
		if (foundUser==null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundUser); 
	}
	
	@PostMapping
	public ResponseEntity<User> saveUsuario(@RequestBody User user)
	{
		User savedUser=userService.saveUser(user);
		return ResponseEntity.ok(savedUser);
	}
	
	@GetMapping("/carros/{idUser}")
	public ResponseEntity<List<Car>> listarCarrosByUsuario(@PathVariable Long idUser)
	{
		User userFound=userService.getUserById(idUser);
		if (userFound==null)
		{
			return ResponseEntity.notFound().build();
		}
		List<Car> listaCarros=userService.getAllCarByUsuario(idUser);
		if (listaCarros==null || listaCarros.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaCarros);
	}
	
	@GetMapping("/motos/{idUser}")
	public ResponseEntity<List<Moto>> listaMotosByUsuario(@PathVariable Long idUser)
	{
		User foundUser= userService.getUserById(idUser);
		if (foundUser==null)
		{
			return ResponseEntity.notFound().build();
		}
		
		List<Moto> listaMotos=userService.getAllMotoByUsuario(idUser);
		if (listaMotos==null || listaMotos.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaMotos);
	}
	
	@PostMapping("/carros/{idUser}")
	public ResponseEntity<Car> saveCarro(@PathVariable Long idUser,@RequestBody Car car)
	{
		User foundUser=userService.getUserById(idUser);
		if (foundUser==null)
		{
			return ResponseEntity.notFound().build();
		}
		Car newCar=userService.saveCarFeignClient(car, idUser);
		return ResponseEntity.ok(newCar);
	}
	
	@PostMapping("/motos/{idUser}")
	public ResponseEntity<Moto> saveMoto(@PathVariable Long idUser,@RequestBody Moto moto)
	{
		User foundUser=userService.getUserById(idUser);
		if (foundUser==null)
		{
			return ResponseEntity.notFound().build();
		}
		Moto newMoto=userService.saveMotoFeignClient(moto,idUser);
		return ResponseEntity.ok(newMoto);
	}
	
	@GetMapping("/todos/{idUser}")
	public ResponseEntity<Map<String,Object>> listarTodosLosVehiculos(@PathVariable Long idUser)
	{
		Map<String,Object> resultado=userService.getUserAndVehiclesFeignClient(idUser);
		return ResponseEntity.ok(resultado);
	}
 
}
