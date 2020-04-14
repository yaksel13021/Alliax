package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.Factura;

@Configuration
public class FacturasConfig {
	
	@Bean
	public List<Factura> facturas(){
		
		List<Factura> resultados = new ArrayList<Factura>();
		
		Factura factura = new Factura();
		factura.setDocFactura("2000107116");
		factura.setFechaFactura(new Date());
		factura.setMoneda("MXP");
		factura.setMontoNeto(new BigDecimal("4537.01"));
		factura.setMontoImp(new BigDecimal("28356.32"));
		
		factura = new Factura();
		factura.setDocFactura("2000107301");
		factura.setFechaFactura(new Date());
		factura.setMoneda("MXP");
		factura.setMontoNeto(new BigDecimal("626.68"));
		factura.setMontoImp(new BigDecimal("100.27"));		
		
		resultados.add(factura);
		
		return resultados;

	}

}
