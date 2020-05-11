package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.alliax.portalclientes.model.MetodoPagoCFDI;

@Configuration
public class BuscarMetodoPagoCfdiConfig {
	
	private final static Logger logger = Logger.getLogger(BuscarMetodoPagoCfdiConfig.class);

	public MetodoPagoCFDI buscarMetodoPagoCFDI(String nroCliente) {
		logger.info("BuscarMetodoPagoCfdiConfig - buscarMetodoPagoCFDI, nroCliente: " + nroCliente);
		MetodoPagoCFDI resultado = new MetodoPagoCFDI();
		resultado.setClaveMetodoPago("PUE");	
		resultado.setResultCode("0");	
		return resultado;
	}
}
