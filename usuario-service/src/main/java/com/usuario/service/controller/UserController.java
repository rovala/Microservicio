package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.User;
import com.usuario.service.models.Bicycle;
import com.usuario.service.models.Car;
import com.usuario.service.models.Moto;
import com.usuario.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
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
	
	@CircuitBreaker(name="carrosCB",fallbackMethod="fallbackGetCarros")
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
	
	@CircuitBreaker(name="motosCB",fallbackMethod="fallbackGetMotos")
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
	
	@CircuitBreaker(name="bicicletasCB",fallbackMethod="fallbackGetBicicletas")
	@GetMapping("/bicicletas/{idUser}")
	public ResponseEntity<List<Bicycle>> listaBicicletasByUsuario(@PathVariable Long idUser)
	{
		User foundUser=userService.getUserById(idUser);
		if (foundUser==null)
		{
			return ResponseEntity.notFound().build();
		}
		List<Bicycle> listaBicicletas=userService.getAllBicycleByUsuario(idUser);
		if (listaBicicletas.isEmpty()||listaBicicletas==null)
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listaBicicletas);
	}
	
	@CircuitBreaker(name="carrosCB",fallbackMethod="fallbackGetSaveCarro")
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
	
	@CircuitBreaker(name="carrosCB",fallbackMethod="fallbackSaveMoto")
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
	
	@CircuitBreaker(name="bicicletasCB",fallbackMethod="fallbackSaveBicicleta")
	@PostMapping("/bicicletas/{idUser}")
	public ResponseEntity<Bicycle> saveBicicleta(@PathVariable Long idUser, @RequestBody Bicycle bicycle)
	{
		User foundUser=userService.getUserById(idUser);
		if (foundUser==null)
		{
			return ResponseEntity.noContent().build();
		}
		Bicycle newBicycle=userService.saveBicycleFeignClient(bicycle, idUser);
		return ResponseEntity.ok(newBicycle);
	}
	
	@CircuitBreaker(name="todosCB",fallbackMethod="fallbackGetTodos")
	@GetMapping("/todos/{idUser}")
	public ResponseEntity<Map<String,Object>> listarTodosLosVehiculos(@PathVariable Long idUser)
	{
		Map<String,Object> resultado=userService.getUserAndVehiclesFeignClient(idUser);
		return ResponseEntity.ok(resultado);
	}
	
	private ResponseEntity<List<Car>> fallbackGetCarros(@PathVariable Long idUser,RuntimeException e)
	{
		return new ResponseEntity("Carros no disponibles para el usuario " + idUser, HttpStatus.OK);
	}
	
	private ResponseEntity<Car> fallbackSaveCarro(@PathVariable Long idUser, @RequestBody Car car,RuntimeException e)
	{
		return new ResponseEntity("El usuario " + idUser+ " no tiene dinero para comprar carros", HttpStatus.OK);
	}
	
	private ResponseEntity fallbackGetMotos(@PathVariable Long idUser,RuntimeException e)
	{
		return new ResponseEntity("Motos no disponibles para el usuario " + idUser, HttpStatus.OK);
	}
	
	private ResponseEntity<Moto> fallbackSaveMoto(@PathVariable Long idUser, @RequestBody Moto moto,RuntimeException e)
	{
		return new ResponseEntity("El usuario " + idUser+ " no tiene dinero para comprar motos", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Bicycle>> fallbackGetBicicletas(@PathVariable Long idUser,RuntimeException e)
	{
		return new ResponseEntity("Bicicletas no disponibles para el usuario " + idUser,HttpStatus.OK);
	}
	
	private ResponseEntity<Bicycle> fallbackSaveBicicleta(@PathVariable Long idUser,RuntimeException e)
	{
		return new ResponseEntity("El usuario "+idUser+" no tiene dinero para comprar bicicletas",HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String,Object>> fallbackGetTodos(@PathVariable Long idUser,RuntimeException e)
	{
		return new ResponseEntity("El usuario " + idUser+ " tiene los vehiculos en el taller", HttpStatus.OK);
	}
}
