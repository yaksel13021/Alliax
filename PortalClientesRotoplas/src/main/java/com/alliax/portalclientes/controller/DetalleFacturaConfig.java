package com.alliax.portalclientes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.model.ItemFactura;

@Configuration
public class DetalleFacturaConfig {

	@Bean
	public List<ItemFactura> detalleFacturas(){
		List<ItemFactura> resultados = new ArrayList<ItemFactura>();
		
		ItemFactura fact;
		fact = new ItemFactura();
		
		fact.setDocFactura("1");
		fact.setPosicion(1);
		fact.setMaterial("200039");
		fact.setDescripcion("CONECTOR HEMBRA DE 20 MM x 1/2");
		fact.setCantidad(new BigDecimal("60.000"));
		fact.setUnidadMedida("PZA");
		fact.setMontoNeto(new BigDecimal("653.98"));
		fact.setMoneda("MXP");
		fact.setImpuesto(new BigDecimal("104.64"));
		
		resultados.add(fact);
		
		
		fact = new ItemFactura();
		
		fact.setDocFactura("2");
		fact.setPosicion(2);
		fact.setMaterial("200042");
		fact.setDescripcion("CONECTOR HEMBRA DE 20 MM x 1/2");
		fact.setCantidad(new BigDecimal("50.000"));
		fact.setUnidadMedida("PZA");
		fact.setMontoNeto(new BigDecimal("735.87"));
		fact.setMoneda("MXP");
		fact.setImpuesto(new BigDecimal("117.64"));
		
		resultados.add(fact);		
				
		
		return resultados;
	}
}
