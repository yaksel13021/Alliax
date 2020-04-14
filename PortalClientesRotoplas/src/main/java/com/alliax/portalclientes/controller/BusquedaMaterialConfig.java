package com.alliax.portalclientes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.Material;

@Configuration
public class BusquedaMaterialConfig {

	@Bean
	public List<Material> busquedaMateriales(){
		List<Material> materiales = new ArrayList<Material>();
		
		Material material = new Material();
		
		material.setNoMaterial("1234567890");
		material.setDescripcion("Tinaco de 300 lts");		
		materiales.add(material);
		
		material = new Material();		
		material.setNoMaterial("9876543210");
		material.setDescripcion("Codo de 3 pulgadas");		
		materiales.add(material);
		
		material = new Material();
		material.setNoMaterial("9876543210");
		material.setDescripcion("Filtro de Agua con flujo de 200 lts/seg");		
		materiales.add(material);
		
		return materiales;
		
	}
}
