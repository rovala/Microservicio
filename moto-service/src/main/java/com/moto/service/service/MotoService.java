package com.moto.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.service.entity.Moto;
import com.moto.service.repository.MotoRepository;

@Service
public class MotoService
{
	@Autowired
	private MotoRepository motoRepository;
	
	public List<Moto> getAll()
	{
		return motoRepository.findAll();
	}
	
	public Moto getMotoById(Long idMoto)
	{
		return motoRepository.findById(idMoto).orElse(null);
	}
	
	public Moto saveMoto(Moto moto)
	{
		return motoRepository.save(moto);
	}
	
	public List<Moto> getAllByIdUser(Long idUser)
	{
		return motoRepository.findByIdUser(idUser);
	}
}
