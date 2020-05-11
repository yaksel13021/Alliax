package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.ClasePedido;

@Configuration
public class BuscarClasePedidoConfig {
	
	private final static Logger logger = Logger.getLogger(BuscarClasePedidoConfig.class);
	
	public ClasePedido buscarClasePedido(String organizacionVentas, String codigoPostal) {
		logger.info("BuscarClasePedidoConfig - buscarClasePedido, organizacionVentas: " + organizacionVentas + ", codigoPostal" + codigoPostal);
		ClasePedido result = new ClasePedido();
		result.setClasePedido("Clase Pedido 1");
		result.setResultCode("0");
		return result;
	}

}
