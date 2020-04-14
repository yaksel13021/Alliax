package com.alliax.portalclientes.service;

import java.util.List;
import java.util.Map;

import com.alliax.portalclientes.domain.Pais;

public interface PaisService {
	
	public List<Pais> findAll();
	
	public Map<Integer,String> findAllSet();
	
	public Pais findById(int idPais);
	
	public Pais save(Pais pais);

}
