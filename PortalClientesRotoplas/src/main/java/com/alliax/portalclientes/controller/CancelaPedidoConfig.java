package com.alliax.portalclientes.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelaPedidoConfig {
	
private final static Logger logger = Logger.getLogger(CancelaPedidoConfig.class);
	
	public String cancelaPedido(String nroPedido) {
		logger.info("CancelaPedidoConfig - cancelaPedido, nroPedido: " + nroPedido);
		String result = "0";
		return result;
	}

}
