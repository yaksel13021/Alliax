package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.StockMaterial;

@Configuration
public class StockMaterialConfig {
	
	@Bean
	public List<StockMaterial> stockMaterial(){
		List<StockMaterial> resultado = new ArrayList<StockMaterial>();
		
		DecimalFormat df = new DecimalFormat("#,###.00");
		
		StockMaterial stock = new StockMaterial();
		stock.setNoMaterial("98234566");
		stock.setDescripcion("Tinaco Extra grande Cap 500Lts");
		stock.setNoPlanta("1234");
		stock.setPlanta("Gdl");
		stock.setAlmacen("0001");
		stock.setStock(new BigDecimal("4000"));
		stock.setUmb("Pieza");
		
		resultado.add(stock);
				
		stock = new StockMaterial();
		stock.setNoMaterial("98234566");
		stock.setDescripcion("Valvla una via");
		stock.setNoPlanta("3112");
		stock.setPlanta("Leo");
		stock.setAlmacen("0002");
		stock.setStock(new BigDecimal("400000"));
		stock.setUmb("Pieza");		
		
		resultado.add(stock);
		
		return resultado;		
	}
}
