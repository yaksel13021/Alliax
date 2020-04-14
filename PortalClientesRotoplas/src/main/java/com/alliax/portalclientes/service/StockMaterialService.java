package com.alliax.portalclientes.service;

import java.util.List;

import com.alliax.portalclientes.model.StockMaterial;

public interface StockMaterialService {
	
	public List<StockMaterial> getResultado();
	
	public List<StockMaterial> obtieneStock(String noMaterial, int planta, String lang);
	
}
