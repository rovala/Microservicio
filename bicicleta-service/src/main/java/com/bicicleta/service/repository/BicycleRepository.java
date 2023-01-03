package com.bicicleta.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bicicleta.service.entity.Bicycle;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Long>
{
	List<Bicycle> findByIdUser(Long idUser);
}
