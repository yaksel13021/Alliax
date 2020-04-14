package com.alliax.portalclientes.service;

import java.util.List;
import java.util.Map;

import com.alliax.portalclientes.domain.Planta;

public interface PlantaService {
	
	public List<Planta> findAll();
	
	public Map<String,String> findAllMap();
	
	public Map<String,String> findByLocalidad(int idLocalidad);
	
	public Planta findByIdLocalidad(int idLocalidad);
	
	public Planta findById(int idPlanta);
	
	public Planta save(Planta planta);
	
}
