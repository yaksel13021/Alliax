package com.alliax.portalclientes.service;

import java.util.List;
import java.util.Map;

import com.alliax.portalclientes.domain.Estado;

public interface EstadoService {
	public List<Estado> findAll();
	
	public Map<Integer,String> findAllByPaisSet(int idPais);
	
	public Map<Integer,String> findByPlanta(String descripcion);
	
	public Estado findById(int idPais);
	
	public Estado save(Estado pais);
}
