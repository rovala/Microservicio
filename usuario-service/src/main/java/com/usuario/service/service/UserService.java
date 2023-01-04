package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.User;
import com.usuario.service.feignclients.BicycleFeignClient;
import com.usuario.service.feignclients.CarFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.models.Bicycle;
import com.usuario.service.models.Car;
import com.usuario.service.models.Moto;
import com.usuario.service.repository.UserRepository;

@Service
@SuppressWarnings("unchecked")
public class UserService
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarFeignClient carroFeignClient;
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	@Autowired
	private BicycleFeignClient bicycleFeignClient;
	
	public List<User> getAll()
	{
		return userRepository.findAll();
	}
	
	public User getUserById(Long id)
	{
		return userRepository.findById(id).orElse(null);
	}
	
	public User saveUser(User user)
	{
		User savedUser=userRepository.save(user);
		return savedUser;
	}
	
	public List<Car> getAllCarByUsuario(Long idUser)//COMUNICACION RESTTEMPLATE, posd: metodos get, post, corresponde al nombre de la funcion, ejemplo: getForOb... o postForOb...
	{
		List<Car> listaCarros=restTemplate.getForObject("http://carro-service/carro/usuario/"+idUser, List.class);
		return listaCarros;
	}
	
	public List<Moto> getAllMotoByUsuario(Long idUser)//COMUNICACION RESTTEMPLATE, posd: metodos get, post, corresponde al nombre de la funcion, ejemplo: getForOb... o postForOb...
	{
		List<Moto> listaMotos=restTemplate.getForObject("http://moto-service/moto/usuario/"+idUser, List.class);
		return listaMotos;
	}
	
	public List<Bicycle> getAllBicycleByUsuario(Long idUser)//COMUNICACION RESTTEMPLATE, posd: metodos get, post, corresponde al nombre de la funcion, ejemplo: getForOb... o postForOb...
	{
		List<Bicycle> listaBicicleta=restTemplate.getForObject("http://bicicleta-service/bicicleta/usuario/"+idUser, List.class);
		return listaBicicleta;
	}
	
	public Car saveCarFeignClient(Car car,Long idUser)
	{
		car.setIdUser(idUser);
		return carroFeignClient.save(car);
	}
	
	public Moto saveMotoFeignClient(Moto moto, Long idUser)
	{
		moto.setIdUser(idUser);
		return motoFeignClient.save(moto);
	}
	
	public Bicycle saveBicycleFeignClient(Bicycle bicycle, Long idUser)
	{
		bicycle.setIdUser(idUser);
		return bicycleFeignClient.Save(bicycle);		
	}
	
	public Map<String,Object> getUserAndVehiclesFeignClient(Long idUser)
	{
		Map<String,Object> listaVehiculos=new HashMap<>();
		User usuario=userRepository.findById(idUser).orElse(null);
		if (usuario==null)
		{
			listaVehiculos.put("Mensaje","Usuario no encontrado");
		}
		else
		{
			listaVehiculos.put("Usuario: ",usuario);
			List<Car> listaCars=carroFeignClient.getCars(idUser);
			if (listaCars==null || listaCars.isEmpty())
			{
				listaVehiculos.put("Carros: ","Usuario no tiene carros");
			}
			else
			{
				listaVehiculos.put("Carros: ",listaCars);
			}
			List<Moto> listaMotos=motoFeignClient.getMotos(idUser);
			if (listaMotos==null || listaMotos.isEmpty())
			{
				listaVehiculos.put("Motos: ","Usuario no tiene motos");
			}
			else
			{
				listaVehiculos.put("Motos: ",listaMotos);
			}
			List<Bicycle> listaBicicletas=bicycleFeignClient.getBicycles(idUser);
			if (listaBicicletas.isEmpty()||listaBicicletas==null)
			{
				listaVehiculos.put("Bicicletas: ","Usuario no tiene bicicletas");
			}
			else
			{
				listaVehiculos.put("Bicicletas: ",listaBicicletas);
			}
		}
		return listaVehiculos;
	}
}
