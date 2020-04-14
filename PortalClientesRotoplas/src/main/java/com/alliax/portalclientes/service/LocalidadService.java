package com.alliax.portalclientes.service;

import java.util.List;
import java.util.Map;

import com.alliax.portalclientes.domain.Localidad;

public interface LocalidadService {
	public List<Localidad> findAll();
	
	public Map<Integer,String> findAllByEstadoSet(int idEstado);
	
	public Localidad findById(int idLocalidad);
	
	public Localidad save(Localidad localidad);
}
